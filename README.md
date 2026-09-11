## GrinOps - Prova de Conceito (PoC)
Projeto desenvolvido como parte da Atividade de Estudo Programada (AEP) do curso de Engenharia de Software (6 Semestre) da Unicesumar.

## ODS Atendidos (Objetivos de Desenvolvimento Sustentavel)
ODS 7 e ODS 12: Energia Acessivel e Limpa / Consumo e Producao Responsáveis.
O problema: O desperdício de recursos computacionais e energéticos em servidores ociosos gera custos operacionais e impacto ambiental. O GrinOps monitora, gera alertas e registra tratativas para otimizar o consumo de TI.

## Camadas e Arquitetura
Controllers: Endpoints REST (AlertaDesperdicioController, MetricaConsumoController, ServidorController) para requisicoes HTTP e respostas JSON.
Services: Regras de negócio (AnaliseConsumoService, MetricaConsumoService).
Repositories: Interfaces com MongoDB (AlertaDesperdicioRepository, MetricaConsumoRepository, ServidorRepository).
Models: Entidades (AlertaDesperdicio, ConfiguracaoHardware, Container, MetricaConsumo, Servidor, Tratativa).

## Tecnologias
Java 21
Spring Boot 3.2.5
MongoDB
Lombok, Maven, JUnit 5 e Mockito

## Execução
1. Clone o repositório.
2. Navegue até a pasta do projeto.
3. Execute: ./mvnw.cmd spring-boot:run
