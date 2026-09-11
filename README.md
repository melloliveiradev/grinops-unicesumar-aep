## GrinOps - Prova de Conceito (PoC)
Projeto desenvolvido como parte da Atividade de Estudo Programada (AEP) do curso de Engenharia de Software (6 Semestre) da Unicesumar.

## ODS Atendidos (Objetivos de Desenvolvimento Sustentavel)
ODS 7 e ODS 12: Energia Acessivel e Limpa / Consumo e Producao Responsaveis.
O problema: O desperdicio de recursos computacionais e energeticos em servidores ociosos gera custos operacionais e impacto ambiental. O GrinOps monitora, gera alertas e registra tratativas para otimizar o consumo de TI.

## Camadas e Arquitetura
Controllers: Endpoints REST (AlertaDesperdicioController, MetricaConsumoController, ServidorController) para requisicoes HTTP e respostas JSON.
Services: Regras de negocio (AnaliseConsumoService, MetricaConsumoService).
Repositories: Interfaces com MongoDB (AlertaDesperdicioRepository, MetricaConsumoRepository, ServidorRepository).
Models: Entidades (AlertaDesperdicio, ConfiguracaoHardware, Container, MetricaConsumo, Servidor, Tratativa).

## Tecnologias
Java 21
Spring Boot 3.2.5
MongoDB
Lombok, Maven, JUnit 5 e Mockito

## Execucao
1. Clone o repositorio.
2. Navegue ate a pasta do projeto.
3. Execute: ./mvnw.cmd spring-boot:run
