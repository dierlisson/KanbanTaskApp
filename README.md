# 📋 KanbanTask App

Um aplicativo Android moderno e responsivo para gerenciamento de tarefas baseado na metodologia Kanban. Desenvolvido para demonstrar o uso prático de arquiteturas modernas, interfaces declarativas e testes automatizados no ecossistema Android.

## 🚀 Sobre o Projeto

O **KanbanTask** permite aos usuários organizar suas demandas diárias em três colunas clássicas: *A Fazer*, *Fazendo* e *Concluído*. O aplicativo consome uma API RESTful para persistência dos dados em nuvem e utiliza um visual limpo baseado no Material Design 3. 

A navegação entre as colunas foi projetada para oferecer uma experiência fluida através de um carrossel horizontal, mantendo o foco na coluna ativa e permitindo a rápida adição ou edição de tarefas através de modais interativos (Bottom Sheets).

## ✨ Funcionalidades

* **Visualização Kanban:** Navegação por páginas horizontais (Carrossel) simulando quadros do Trello/Jira.
* **CRUD Completo:** Adição, edição, listagem e exclusão de tarefas persistidas em nuvem.
* **Progressão Rápida:** Mudança de status da tarefa (TODO -> DOING -> DONE) com apenas um toque no cartão.
* **Interface Dinâmica:** Cartões com altura dinâmica e colunas alinhadas no topo para melhor aproveitamento de tela.
* **Splash Screen Integrada:** Animação de entrada nativa do Android 12+ utilizando a biblioteca `core-splashscreen`.
* **Ícone Adaptativo:** Design de ícone que se adapta aos padrões do sistema operacional do usuário.

## 🛠 Tecnologias Utilizadas

O projeto foi construído utilizando o que há de mais moderno no desenvolvimento nativo Android:

* **Kotlin:** Linguagem principal do projeto.
* **Jetpack Compose:** Toolkit moderno e declarativo para construção da UI.
    * `HorizontalPager` para o efeito de carrossel.
    * `ModalBottomSheet` para formulários sobrepostos.
* **Arquitetura MVVM (Model-View-ViewModel):** Separação clara de responsabilidades entre a lógica de negócios e a interface.
* **Coroutines & StateFlow:** Gerenciamento de tarefas assíncronas e reatividade baseada em estados.
* **Retrofit & Gson:** Cliente HTTP para comunicação com a API RESTful e conversão de JSON.
* **MockAPI.io:** Backend as a Service utilizado para gerar os endpoints REST.
* **Testes:**
    * **JUnit4 & MockK:** Testes unitários focados nas regras de negócio e chamadas de API do ViewModel.
    * **Compose UI Testing:** Testes instrumentados (UI) para simular interações do usuário e validar a renderização condicional da interface.

---

## 🧠 Desafios Superados e Aprendizados

Durante o desenvolvimento deste aplicativo, diversos desafios técnicos foram encontrados e resolvidos, proporcionando um grande aprofundamento no framework do Android:

### 1. Gestão de Estado e Navegação com Pager
**Desafio:** Substituir listas simples (`LazyRow`) por um `HorizontalPager` para criar um efeito de carrossel imersivo, mantendo o alinhamento das colunas no topo e calculando a altura dinamicamente (`wrapContentHeight`).
**Aprendizado:** Aprofundamento no sistema de layout do Jetpack Compose, entendendo o comportamento de modificadores complexos e como o Pager lida com reciclagem de views em comparação com as listas tradicionais.

### 2. Sincronização Estrita de Tipos de Dados da API
**Desafio:** Após conectar o Retrofit, a conexão funcionava (status 200), mas os dados não apareciam na tela.
**Solução e Aprendizado:** Identifiquei que geradores automáticos de mock inseriram strings aleatórias (ex: "status 1") no campo `status`. Como a UI do Compose filtrou os estados usando validação estrita ("TODO", "DOING", "DONE"), os dados não renderizavam. Isso reforçou a importância do mapeamento preciso (Schema) entre os DTOs do backend e as entidades do frontend, além do uso eficaz do **Logcat** para interceptar e analisar os payloads que chegam da API.

### 3. Conflitos de Resolução de Dependências do Gradle
**Desafio:** Ao rodar os testes instrumentados de UI, o Gradle falhou com um erro crítico apontando que o Compose UI Testing exigia estritamente (`strictly`) o `espresso-core:3.5.0`, entrando em conflito com outras bibliotecas do projeto.
**Solução e Aprendizado:** A resolução de dependências no Gradle requer atenção às "Bills of Materials" (BOMs). Compreendi como forçar versões específicas de bibliotecas no arquivo `build.gradle.kts` para garantir que o ambiente de testes do Compose não conflitasse com o core do Android.

### 4. Ambiguidade de Nós (Nodes) no Compose Testing
**Desafio:** O teste instrumentado do formulário de edição falhou com o erro `Expected at most 1 node but found 2 nodes`. O Compose Testing encontrou a string "Tarefa a Editar" tanto no formulário (Bottom Sheet) quanto no cartão que ficou em segundo plano.
**Solução e Aprendizado:** Entendimento de como o Jetpack Compose monta a sua Árvore Semântica. Aprendi a utilizar a função `onAllNodesWithText(texto).assertCountEquals(2)` para lidar com elementos sobrepostos na mesma tela, garantindo que o teste compreenda que a view de fundo ainda existe na hierarquia, mesmo esmaecida.

### 5. Configuração Correta do Ambiente de Testes
**Desafio:** Erros de `NullPointerException` (como `Build.FINGERPRINT is null`) ao tentar rodar os testes de UI do Compose.
**Aprendizado:** Diferenciação clara entre o ambiente da JVM (Testes Unitários locais, que rodam no computador e exigem mocks como o *MockK*) e o ambiente Instrumentado (Testes de UI, que exigem um emulador/dispositivo real com o sistema Android para acessar recursos gráficos e de acessibilidade).

---
## 🎥 Demonstração do App

Confira abaixo o KanbanTask em funcionamento. No vídeo, demonstramos o fluxo completo de uso e a responsividade da interface construída com Jetpack Compose:

* **Splash Screen Integrada:** Abertura do app utilizando a API nativa do Android com o ícone adaptativo personalizado.
* **Gestão Ágil:** Fluxo rápido e intuitivo para editar tarefas existentes e criar novas demandas utilizando o modal inferior (Bottom Sheet).
* **Navegação Fluida:** Transição suave entre as colunas do quadro Kanban ("A Fazer", "Fazendo" e "Concluído") através do `HorizontalPager`.
* **Persistência em Nuvem:** Ao final da demonstração, o aplicativo é fechado e reaberto, provando que o estado e os dados das tarefas são carregados com sucesso diretamente da API (Retrofit).

<video src="https://github.com/user-attachments/assets/8c0cce8f-963b-44fd-aee6-7ff32548c657" controls="controls" style="max-width: 100%; height: auto;">
  Seu navegador não suporta a tag de vídeo.
</video>
---

## 💻 Como Executar o Projeto

1. Clone este repositório:
   ```bash
   git clone https://github.com/dierlisson/KanbanTaskApp.git
   ```
2. Abra o projeto no Android Studio (versão Iguana ou superior recomendada).

3. Aguarde o Gradle sincronizar todas as dependências.

4. Conecte um dispositivo Android físico ou inicie o Emulador.

5. Clique no botão de Run (Shift + F10).

> **🔐 Nota sobre a API e Segurança:** O aplicativo está configurado para consumir um endpoint público do MockAPI.io. Caso queira usar seu próprio backend, altere a variável `BASE_URL` no arquivo `RetrofitClient.kt`. 
> 
> **Boas Práticas:** Em um aplicativo real de produção, a melhor prática seria ocultar essa URL base e quaisquer tokens de acesso no arquivo `local.properties` (injetando os valores através do `BuildConfig`), garantindo que dados sensíveis não subam para o repositório no GitHub. Como este projeto tem fins estritamente didáticos e de portfólio, e o objetivo principal foi consolidar a interface gráfica e a arquitetura MVVM, optei por manter a URL diretamente no código para facilitar a execução rápida e a avaliação.

🧪 Como Rodar os Testes
Testes Unitários (ViewModel):
Navegue até app/src/test/.../viewmodel/TaskViewModelTest.kt e clique no ícone de execução na margem esquerda da IDE.

Testes de UI (Jetpack Compose):
Com o emulador aberto, navegue até app/src/androidTest/.../ui/KanbanBoardScreenTest.kt e clique no ícone de execução do Android.

## 👤 Autor

Desenvolvido por **Dierlisson Justiniano** como parte de um desafio prático de desenvolvimento Android.
