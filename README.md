# Sistema de Reservas de Áreas Comuns - Condomínio

Sistema CLI em Java para gerenciamento de reservas de áreas comuns de condomínios, utilizando MySQL como banco de dados local.

---

## Tecnologias

- Java 17+
- MySQL 8.x
- JDBC
- Maven

---

## Estrutura do Projeto

```
src/main/java/com/condominio/
├── config/
│   └── DatabaseConnection.java
├── model/
│   ├── Condominio.java
│   ├── AreaComum.java
│   ├── Morador.java
│   └── Reserva.java
├── repository/
│   ├── CondominioRepository.java
│   ├── AreaComumRepository.java
│   ├── MoradorRepository.java
│   └── ReservaRepository.java
├── service/
│   ├── CondominioService.java
│   ├── AreaComumService.java
│   ├── MoradorService.java
│   └── ReservaService.java
├── controller/
│   ├── CondominioController.java
│   ├── AreaComumController.java
│   ├── MoradorController.java
│   └── ReservaController.java
└── Main.java
```

---

## Pré-requisitos

1. **Java 17** ou superior instalado
2. **Maven** instalado
3. **MySQL 8** ativo e acessível em `localhost:3306`

---

## Configuração do Banco de Dados

1. Acesse o MySQL:
   ```bash
   mysql -u root -p
   ```

2. Execute o script de criação:
   ```bash
   source sql/init.sql
   ```

   Ou copie e cole o conteúdo do arquivo `sql/init.sql` diretamente no terminal MySQL.

**Configuração padrão de conexão:**
- Host: `localhost`
- Porta: `3306`
- Banco: `condominio_db`
- Usuário: `root`
- Senha: `root`

Para alterar, edite `DatabaseConnection.java`.

---

## Como Executar

```bash
# Compilar e executar
mvn compile exec:java
```

Ou, alternativamente:

```bash
# Compilar
mvn compile

# Executar
mvn exec:java -Dexec.mainClass="com.condominio.Main"
```

---

## Exemplos de Uso

### 1. Criar o Condomínio (configurar regras)

```
Escolha: 1  (Gerenciar Condomínio)
Escolha: 1  (Criar condomínio)

Domingo bloqueado? (S/N): N
Segunda bloqueada? (S/N): S
Terça bloqueada? (S/N): N
Quarta bloqueada? (S/N): N
Quinta bloqueada? (S/N): N
Sexta bloqueada? (S/N): N
Sábado bloqueado? (S/N): N

Horário mínimo Domingo (HH:MM): 10:00
Horário mínimo Terça (HH:MM):
Horário mínimo Quarta (HH:MM):
Horário mínimo Quinta (HH:MM):
Horário mínimo Sexta (HH:MM):
Horário mínimo Sábado (HH:MM): 08:00
```

### 2. Cadastrar Áreas Comuns

```
Escolha: 3  (Gerenciar Áreas Comuns)
Escolha: 1  (Cadastrar)
Nome da área: Churrasqueira

Escolha: 1  (Cadastrar)
Nome da área: Salão de Festas

Escolha: 1  (Cadastrar)
Nome da área: Quadra

Escolha: 1  (Cadastrar)
Nome da área: Piscina
```

### 3. Cadastrar Moradores

```
Escolha: 2  (Gerenciar Moradores)
Escolha: 1  (Cadastrar)
Nome: João da Silva
Número do apartamento: 101

Escolha: 1  (Cadastrar)
Nome: Maria Oliveira
Número do apartamento: 202
```

### 4. Registrar uma Reserva

```
Escolha: 4  (Gerenciar Reservas)
Escolha: 1  (Registrar reserva)
ID do morador: 1
ID da área comum: 1
Data da reserva (AAAA-MM-DD): 2026-03-07
Horário da reserva (HH:MM): 14:00

Reserva registrada com sucesso! ID: 1
```

### 5. Tentar reservar no mesmo horário (conflito)

```
ID do morador: 2
ID da área comum: 1
Data da reserva (AAAA-MM-DD): 2026-03-07
Horário da reserva (HH:MM): 14:00

Erro: Já existe uma reserva para esta área comum nesta data e horário.
```

### 6. Tentar reservar em dia bloqueado (segunda-feira)

```
ID do morador: 1
ID da área comum: 2
Data da reserva (AAAA-MM-DD): 2026-03-09
Horário da reserva (HH:MM): 10:00

Erro: Reservas não são permitidas às Segunda-feiras. Este dia está bloqueado pelo condomínio.
```

---

## Regras de Negócio

| Regra | Descrição |
|-------|-----------|
| Disponibilidade | Não pode haver duas reservas para mesma área + data + horário |
| Dias Bloqueados | Dias marcados como `true` no condomínio não permitem reservas |
| Horário Bloqueado | Reservas antes do horário mínimo definido são rejeitadas |
| Validação Completa | Todas as regras são verificadas antes de criar uma reserva |
