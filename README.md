# RunHub API

API RESTful desenvolvida para a gestão de eventos de corrida de rua, atletas e inscrições, aplicada ao domínio de Esportes e Atividades Físicas.

## Integrantes

- Iago Cardoso Rabello de Souza - https://github.com/IagoCrs
- Guilherme Rosa das Neves Borges da Silva - https://github.com/guiborgesilva

## Problema

Organizadores de corridas de rua e assessorias esportivas enfrentam dificuldades operacionais ao controlar inscrições, limites de vagas por prova e atribuição de numeração de peito por meio de processos manuais ou planilhas isoladas. Esse cenário gera inconsistências nos cadastros, risco de duplicidade e falta de rastreabilidade nos resultados dos participantes.

A RunHub API centraliza essas operações em um serviço backend estruturado, garantindo integridade nos cadastros, controle automatizado de capacidade dos eventos e consulta padronizada de dados.

## Domínio da Aplicação

O sistema modela o fluxo operacional de eventos de corrida de rua por meio de três entidades principais:

- **Athlete (Atleta):** representa o participante cadastrado na plataforma, contendo informações de identificação, categoria técnica e ritmo médio (pace).
- **Event (Evento):** representa a prova esportiva, definindo título, cidade, data de realização, distância em quilômetros, limite máximo de participantes e status atual.
- **Registration (Inscrição):** estabelece o vínculo entre um atleta e um evento, controlando o número de peito gerado, o status da inscrição, a data de registro e o tempo final de conclusão da prova.

## Diagrama UML Inicial

```mermaid
classDiagram
    class Athlete {
        +UUID id
        +String name
        +String email
        +LocalDate birthDate
        +String category
        +Double averagePace
        +LocalDateTime createdAt
    }

    class Event {
        +UUID id
        +String title
        +String city
        +LocalDateTime eventDate
        +Double distanceKm
        +Integer maxParticipants
        +EventStatus status
        +LocalDateTime createdAt
    }

    class Registration {
        +UUID id
        +Integer bibNumber
        +RegistrationStatus status
        +Duration finishTime
        +LocalDateTime registeredAt
    }

    Athlete "1" --> "0..*" Registration : realiza
    Event "1" --> "0..*" Registration : possui
