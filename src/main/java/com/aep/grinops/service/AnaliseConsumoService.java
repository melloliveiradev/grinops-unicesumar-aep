package com.aep.grinops.service;

import com.aep.grinops.model.AlertaDesperdicio;
import com.aep.grinops.model.MetricaConsumo;
import com.aep.grinops.model.Servidor;
import com.aep.grinops.repository.AlertaDesperdicioRepository;
import com.aep.grinops.repository.MetricaConsumoRepository;
import com.aep.grinops.repository.ServidorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnaliseConsumoService {

    private final AlertaDesperdicioRepository alertaDesperdicioRepository;
    private final MetricaConsumoRepository metricaRepository;
    private final ServidorRepository servidorRepository;

    private static final double CUSTO_HORA_VCPU = 0.05;
    private static final double LIMITE_CPU_OCIOSO_PERCENTUAL = 10.0;
    private static final int HORAS_NO_MES = 720; // 24 horas * 30 dias

    /**
     * Retorna o fator de emissão em gCO2/kWh baseado na região da nuvem
     */
    private double obterFatorEmissaoPorRegiao(String regiaoNuvem) {
        if (regiaoNuvem == null || regiaoNuvem.isBlank()) {
            return 385.0; // Padrão us-east-1
        }

        return switch (regiaoNuvem.trim().toLowerCase()) {
            case "sa-east-1"    -> 100.0; // América do Sul (Brasil)
            case "us-east-1"    -> 385.0; // EUA Leste
            case "us-west-1"    -> 250.0; // EUA Oeste
            case "eu-central-1" -> 300.0; // Europa (Frankfurt)
            default             -> 385.0; // Média padrão para regiões não mapeadas
        };
    }

    /**
     * Calcula as gramas de CO2 com base no consumo elétrico e na região
     */
    public double calcularEmissaoCo2(double consumoKwh, String regiaoNuvem) {
        if (consumoKwh < 0) {
            throw new IllegalArgumentException("O consumo de energia não pode ser negativo.");
        }
        double fatorRegiao = obterFatorEmissaoPorRegiao(regiaoNuvem);
        return consumoKwh * fatorRegiao;
    }

    /**
     * Registra uma nova métrica calculando o CO2, salvando a métrica e 
     * disparando automaticamente a avaliação de ociosidade e geração de alertas.
     */
    public MetricaConsumo registrarMetrica(MetricaConsumo metrica, String regiaoNuvem) {
        double co2Calculado = calcularEmissaoCo2(metrica.getConsumoEnergiaKwh(), regiaoNuvem);
        metrica.setEstimativaCo2Gramas(co2Calculado);
        if (metrica.getDataHora() == null) {
            metrica.setDataHora(LocalDateTime.now());
        }
        
        MetricaConsumo metricaSalva = metricaRepository.save(metrica);

        if (metricaSalva.getServidorId() != null) {
            avaliarEAtualizarOciosidade(metricaSalva.getServidorId());
        }

        return metricaSalva;
    }

    /**
     * Avalia se a média de uso de CPU do servidor é menor que 10%
     */
    public boolean avaliarEAtualizarOciosidade(String servidorId) {
        Servidor servidor = servidorRepository.findById(servidorId)
                .orElseThrow(() -> new IllegalArgumentException("Servidor não encontrado: " + servidorId));

        List<MetricaConsumo> metricas = metricaRepository.findByServidorId(servidorId);

        if (metricas.isEmpty()) {
            return false;
        }

        double mediaCpu = metricas.stream()
                .mapToDouble(MetricaConsumo::getUsoCpuPercentual)
                .average()
                .orElse(0.0);

        boolean estaOcioso = mediaCpu < LIMITE_CPU_OCIOSO_PERCENTUAL;
        servidor.setOcioso(estaOcioso);
        servidorRepository.save(servidor);

        if (estaOcioso) {
            gerarAlertaSeNaoExistir(servidor);
        }

        return estaOcioso;
    }

    private void gerarAlertaSeNaoExistir(Servidor servidor) {
        List<AlertaDesperdicio> alertasAbertos = alertaDesperdicioRepository.findByServidorIdAndStatus(
                servidor.getId(), "ABERTO"
        );

        if (alertasAbertos.isEmpty()) {
            int vCpus = 1;
            if (servidor.getConfiguracaoHardware() != null) {
                vCpus = servidor.getConfiguracaoHardware().getVCpus();
            }

            double estimativaPrejuizoMensal = vCpus * CUSTO_HORA_VCPU * HORAS_NO_MES;

            AlertaDesperdicio alerta = new AlertaDesperdicio();
            alerta.setServidorId(servidor.getId());
            alerta.setDataCriacao(LocalDateTime.now());
            alerta.setStatus("ABERTO");
            alerta.setCustoEstimadoDesperdicado(estimativaPrejuizoMensal);

            alertaDesperdicioRepository.save(alerta);
        }
    }
}