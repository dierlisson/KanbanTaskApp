# 📋 KanbanTaskApp

Aplicativo Android para gerenciamento de tarefas em um quadro Kanban, desenvolvido com Kotlin, Jetpack Compose, MVVM e integração com API REST.

## 🚀 Sobre o projeto

O **KanbanTaskApp** permite organizar tarefas em três etapas de um fluxo Kanban:

* **A Fazer**
* **Fazendo**
* **Concluído**

As tarefas são persistidas em uma API REST e podem ser cadastradas, editadas, excluídas e movimentadas entre os diferentes status.

A interface foi construída com Jetpack Compose e utiliza um carrossel horizontal para navegar entre as colunas. Os formulários de criação e edição são apresentados em Bottom Sheets, mantendo o usuário no contexto do quadro.

---

## 🎥 Demonstração

O vídeo apresenta:

* abertura do aplicativo com Splash Screen;
* navegação entre as colunas do quadro;
* criação de tarefas;
* edição de tarefas existentes;
* alteração do status;
* persistência dos dados por meio da API REST.

<video src="https://github.com/user-attachments/assets/8c0cce8f-963b-44fd-aee6-7ff32548c657" controls="controls" style="max-width: 100%; height: auto;">
  Seu navegador não suporta a reprodução do vídeo.
</video>

---

## ✨ Funcionalidades

* **Quadro Kanban:** organização das tarefas nas colunas A Fazer, Fazendo e Concluído.
* **CRUD de tarefas:** criação, consulta, edição e exclusão de tarefas.
* **Alteração de status:** movimentação das tarefas entre os diferentes estágios do fluxo.
* **Persistência remota:** armazenamento e recuperação das tarefas por meio de uma API REST.
* **Navegação horizontal:** utilização de `HorizontalPager` para alternar entre as colunas.
* **Formulários em Bottom Sheets:** criação e edição de tarefas sem sair da tela principal.
* **Interface reativa:** atualização da tela a partir dos estados observados pelo ViewModel.
* **Splash Screen:** tela de abertura utilizando a biblioteca `core-splashscreen`.
* **Ícone adaptativo:** ícone compatível com os diferentes formatos adotados pelo Android.

---

## 🏗️ Arquitetura

O projeto utiliza o padrão de apresentação **MVVM — Model-View-ViewModel**.

### Model

Representa os dados das tarefas recebidos e enviados para a API.

Cada tarefa possui informações como:

* identificador;
* título;
* descrição;
* status.

### ViewModel

O `TaskViewModel` concentra as operações relacionadas às tarefas:

* carregar tarefas;
* criar uma tarefa;
* editar título e descrição;
* alterar o status;
* excluir uma tarefa.

As operações assíncronas são executadas com Kotlin Coroutines, enquanto a lista de tarefas é disponibilizada para a interface por meio de `StateFlow`.

### View

A interface foi desenvolvida com Jetpack Compose e observa os dados disponibilizados pelo ViewModel.

Entre os componentes utilizados estão:

* `HorizontalPager`;
* `ModalBottomSheet`;
* componentes do Material Design 3;
* cartões de tarefas;
* estados e componentes reutilizáveis.

---

## 🛠️ Tecnologias utilizadas

| Tecnologia             | Aplicação no projeto                                        |
| ---------------------- | ----------------------------------------------------------- |
| **Kotlin**             | Linguagem principal do aplicativo.                          |
| **Jetpack Compose**    | Construção declarativa das telas e componentes.             |
| **Material Design 3**  | Componentes e padrões visuais da interface.                 |
| **MVVM**               | Separação entre interface, estado e operações da aplicação. |
| **StateFlow**          | Observação reativa da lista de tarefas.                     |
| **Kotlin Coroutines**  | Execução das chamadas assíncronas à API.                    |
| **Retrofit**           | Comunicação com os endpoints REST.                          |
| **Gson**               | Conversão entre JSON e objetos Kotlin.                      |
| **MockAPI.io**         | Backend utilizado para persistir as tarefas do projeto.     |
| **HorizontalPager**    | Navegação horizontal entre as colunas Kanban.               |
| **JUnit 4**            | Execução dos testes unitários.                              |
| **MockK**              | Criação de mocks nos testes unitários.                      |
| **Compose UI Testing** | Testes instrumentados da interface.                         |
| **Gradle**             | Build e gerenciamento das dependências.                     |

---

## 🧠 Desafios superados e aprendizados

### 1. Gestão de estado e navegação com HorizontalPager

**Desafio:** substituir uma lista horizontal simples por um `HorizontalPager`, mantendo as colunas alinhadas no topo e ajustando a altura dos componentes ao conteúdo.

**Solução:** reorganização da estrutura da interface e dos modificadores utilizados nos containers de cada página.

**Aprendizado:** compreensão mais aprofundada do sistema de layout do Jetpack Compose, do comportamento do Pager e das diferenças em relação a componentes como `LazyRow`.

### 2. Sincronização dos status recebidos pela API

**Desafio:** a API respondia com sucesso, mas algumas tarefas não eram exibidas no quadro.

**Causa:** determinados registros possuíam valores de status diferentes dos esperados pela aplicação. A interface filtrava as tarefas utilizando os valores `TODO`, `DOING` e `DONE`.

**Solução:** análise do payload recebido e correção dos valores cadastrados na API.

**Aprendizado:** importância de manter um contrato consistente entre os dados do backend e os modelos utilizados pelo aplicativo, além do uso do Logcat para analisar respostas da API.

### 3. Conflitos entre dependências de testes

**Desafio:** os testes instrumentados apresentaram conflitos entre versões do Compose UI Testing e do Espresso.

**Solução:** ajuste das versões utilizadas no Gradle e alinhamento das dependências de teste.

**Aprendizado:** melhor compreensão sobre resolução de dependências, Compose BOM e compatibilidade entre bibliotecas do ambiente de testes Android.

### 4. Elementos duplicados na árvore semântica

**Desafio:** durante o teste do formulário de edição, o Compose Testing encontrou dois elementos com o mesmo texto: um no cartão da tarefa e outro no Bottom Sheet.

O teste retornava o erro:

```text
Expected at most 1 node but found 2 nodes
```

**Solução:** utilização de uma consulta que considera todos os elementos encontrados:

```kotlin
onAllNodesWithText(texto).assertCountEquals(2)
```

**Aprendizado:** entendimento de que elementos visualmente sobrepostos podem continuar presentes na árvore semântica do Compose e precisam ser considerados durante os testes.

### 5. Diferença entre testes locais e instrumentados

**Desafio:** ocorreram erros ao tentar executar testes de interface no ambiente local da JVM, incluindo problemas relacionados a propriedades do sistema Android.

**Solução:** separação correta dos ambientes de execução:

* testes unitários executados localmente na JVM;
* testes de interface executados em emulador ou dispositivo Android.

**Aprendizado:** compreensão das responsabilidades e limitações de cada tipo de teste no desenvolvimento Android.

---

## 💻 Como executar

### Pré-requisitos

* Android Studio;
* Android SDK configurado;
* JDK compatível com o projeto;
* emulador Android ou dispositivo físico;
* conexão com a internet para baixar as dependências e acessar a API.

O aplicativo possui suporte mínimo ao **Android 7.0 — API 24**.

### Clonar o repositório

```bash
git clone https://github.com/dierlisson/KanbanTaskApp.git
```

Acesse a pasta do projeto:

```bash
cd KanbanTaskApp
```

Depois:

1. Abra a pasta no Android Studio.
2. Aguarde a sincronização do Gradle.
3. Inicie um emulador ou conecte um dispositivo Android.
4. Execute o aplicativo pelo botão **Run** do Android Studio.

### Configuração da API

O aplicativo está configurado para utilizar um endpoint público do MockAPI.io.

Para utilizar outro backend, altere a propriedade `BASE_URL` no arquivo:

```text
app/src/main/java/com/dierlisson/kanbantaskapp/api/RetrofitClient.kt
```

A URL base não é, por si só, uma informação secreta. Entretanto, tokens, chaves de API ou outras credenciais não devem ser adicionados diretamente ao código ou enviados ao GitHub.

Quando forem necessários, esses valores podem ser configurados por meio de `local.properties` e disponibilizados para o aplicativo utilizando o `BuildConfig`.

---

## 🧪 Como rodar os testes

Os comandos devem ser executados na raiz do projeto.

### Testes unitários

No Windows:

```bash
gradlew.bat testDebugUnitTest
```

No macOS ou Linux:

```bash
./gradlew testDebugUnitTest
```

Os relatórios gerados podem ser encontrados em:

```text
app/build/reports/tests/testDebugUnitTest/index.html
```

### Testes instrumentados de interface

Antes de executar, inicie um emulador ou conecte um dispositivo Android.

No Windows:

```bash
gradlew.bat connectedDebugAndroidTest
```

No macOS ou Linux:

```bash
./gradlew connectedDebugAndroidTest
```

Os relatórios dos testes instrumentados podem ser encontrados em:

```text
app/build/reports/androidTests/connected/index.html
```

---

## 👤 Autor

Desenvolvido por **Dierlisson Justiniano** como projeto de portfólio em desenvolvimento Android.

* [LinkedIn](https://www.linkedin.com/in/dierlissonjustiniano/)
* [GitHub](https://github.com/dierlisson)
