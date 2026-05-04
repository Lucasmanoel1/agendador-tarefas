# 📅 MS-Agendador-Tarefas

Microserviço responsável pelo gerenciamento de tarefas agendadas na plataforma. Persiste tarefas em MongoDB, valida autenticação via JWT e expõe endpoints para criação, consulta, atualização e exclusão de tarefas com suporte a filtro por período.

---

## 🏗️ Arquitetura do sistema

```
Frontend
    └── BFF (bff-agendador) :8083
            ├── MS-Usuario      :8080  → PostgreSQL
            ├── MS-Agendador    :8081  → MongoDB  ← você está aqui
            └── MS-Notificacao  :8082  → SMTP (e-mail)
```

> **Importante:** Os endpoints deste serviço **não devem ser acessados diretamente**. Toda comunicação deve passar pelo BFF.

---

## 🚀 Tecnologias

| Tecnologia | Uso |
|---|---|
| Java 17 | Linguagem |
| Spring Boot 4.0.3 | Framework |
| Spring Security + JWT (JJWT 0.13) | Autenticação |
| Spring Data MongoDB | Persistência |
| MongoDB | Banco de dados NoSQL |
| Spring Cloud OpenFeign | Comunicação com MS-Usuario |
| MapStruct | Mapeamento de objetos |
| Lombok | Redução de boilerplate |
| SonarQube | Qualidade de código |
| Docker | Containerização |
| GitHub Actions | CI/CD |

---

## 📋 Endpoints

| Método | Rota | Descrição | Auth |
|---|---|---|---|
| `POST` | `/tarefas` | Cria nova tarefa | ✅ |
| `GET` | `/tarefas` | Lista todas as tarefas do usuário | ✅ |
| `GET` | `/tarefas/eventos?dataInicial=&dataFinal=` | Busca tarefas por período com status PENDENTE | ✅ |
| `PUT` | `/tarefas?id=` | Atualiza tarefa completa | ✅ |
| `PATCH` | `/tarefas?id=&status=` | Altera apenas o status da tarefa | ✅ |
| `DELETE` | `/tarefas?id=` | Remove tarefa por ID | ✅ |

> ✅ = Requer header `Authorization: Bearer {token}`

### Status possíveis de uma tarefa

```
PENDENTE → NOTIFICADO
PENDENTE → CANCELADO
```

---

## ⚙️ Como executar

### Pré-requisitos
- Docker e Docker Compose instalados

### Com Docker Compose

```bash
docker-compose up --build
```

O serviço subirá em `http://localhost:8081`

### Variáveis de ambiente

| Variável | Descrição |
|---|---|
| `SPRING_DATA_MONGODB_URI` | URI de conexão com o MongoDB |

---

## 🗂️ Estrutura do projeto

```
src/main/java/com/lucasmanoel/agendadortarefas/
├── business/
│   ├── TarefasService.java           # Regras de negócio
│   ├── dto/
│   │   ├── TarefasDTORecords.java    # DTO de tarefa (record)
│   │   └── UsuarioDTO.java           # DTO de usuário (Feign)
│   └── mapper/                       # MapStruct converters
├── controller/
│   └── TarefasController.java        # Endpoints REST
├── infrastructure/
│   ├── client/
│   │   └── UsuarioClient.java        # Feign Client → MS-Usuario
│   ├── entity/
│   │   └── TarefasEntity.java        # Documento MongoDB
│   ├── enums/
│   │   └── StatusNotificacaoEnum.java # PENDENTE | NOTIFICADO | CANCELADO
│   ├── exceptions/                   # Exceções customizadas
│   ├── repository/
│   │   └── TarefasRepository.java    # Repositório MongoDB
│   └── security/                     # JWT + Spring Security
```

---

## 🔗 Repositórios relacionados

- [bff-agendador](https://github.com/Lucasmanoel1/bff-agendador-tarefas) — Gateway de entrada
- [usuario](https://github.com/Lucasmanoel1/usuario) — Gerenciamento de usuários
- [notificacao](https://github.com/Lucasmanoel1/notificacao) — Envio de e-mails
