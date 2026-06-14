# Relatório - Breaking Point Codex

## 1. Introdução

O **Breaking Point Codex** é uma aplicação Android criada para servir como companheiro de consulta e organização para um jogo/RPG. A ideia principal da app é juntar, num só lugar, informação sobre personagens, inimigos, itens, equipas e estratégias.

Para além de consultar informação já existente, o utilizador também pode criar as suas próprias equipas e estratégias, guardar esses dados, sincronizá-los com a cloud e exportar/importar ficheiros para partilha ou backup.

A aplicação foi feita em **Kotlin**, usando **Jetpack Compose** para a interface. Também usa **Firebase Authentication** para login/registo e **Firebase Firestore** para guardar dados online.

## 2. Visão geral da app

Ao abrir a app, o utilizador encontra primeiro um ecrã de login. Depois de entrar, tem acesso ao conteúdo principal da aplicação através de uma barra de navegação inferior.

As secções principais são:

- **Início**: mostra uma saudação ao utilizador, atalhos rápidos e algumas criações recentes.
- **Personagens**: lista personagens disponíveis no Codex.
- **Inimigos**: lista inimigos e os seus comportamentos.
- **Itens**: lista itens e os seus efeitos.
- **Criações**: permite criar, editar, apagar, importar e consultar equipas e estratégias.

A app mistura dados fixos, como personagens e itens base, com dados criados pelo utilizador, como equipas e estratégias personalizadas.

## 3. Organização do projeto

O projeto está dividido de forma relativamente simples:

- `data/model`: contém as classes que representam os dados principais da app.
- `data/mock`: contém dados iniciais usados pela app.
- `data/auth`: trata do login, registo e logout.
- `data/local`: guarda as criações no armazenamento interno do telemóvel.
- `data/cloud`: sincroniza as criações com o Firebase Firestore.
- `data/importexport`: trata da importação e exportação de ficheiros.
- `ui/navigation`: define os ecrãs e as rotas da app.
- `ui/screens`: contém os ecrãs visuais da aplicação.
- `ui/theme`: define cores, tema e tipografia.

Esta divisão ajuda a separar a app por responsabilidades. Os dados ficam numa zona, os ecrãs noutra, e os sistemas de guardar, sincronizar e importar ficam em ficheiros próprios.

## 4. Configuração e arranque da aplicação

### `settings.gradle.kts`

Este ficheiro identifica o projeto Gradle e indica que existe um módulo principal chamado `app`. É uma parte base da estrutura de qualquer projeto Android com Gradle.

### `build.gradle.kts`

Este ficheiro fica na raiz do projeto e define plugins gerais usados pela aplicação. Serve mais como configuração global do projeto.

### `app/build.gradle.kts`

Este é um dos ficheiros de configuração mais importantes. Aqui ficam definidos:

- o `namespace` da app;
- o `applicationId`;
- a versão mínima e alvo do Android;
- o uso de Jetpack Compose;
- as dependências principais da aplicação.

Entre as dependências usadas estão Compose, Material 3, Navigation Compose, Firebase Authentication e Firebase Firestore.

### `AndroidManifest.xml`

Este ficheiro declara a aplicação Android e a atividade principal. Neste caso, a atividade principal é a `MainActivity`, que é aberta quando o utilizador inicia a app.

Também define o ícone, o nome da aplicação e o tema base.

### `google-services.json`

Este ficheiro liga a app ao projeto Firebase. É usado pelo plugin do Google Services para configurar corretamente o Firebase Authentication e o Firestore.

## 5. Sistema principal da app

### `MainActivity.kt`

A `MainActivity` é o ponto de entrada principal da app. É aqui que a interface é montada e que vários sistemas se encontram.

Este ficheiro faz várias coisas importantes:

- aplica o tema visual da app;
- coloca a imagem de fundo global;
- verifica se existe um utilizador autenticado;
- mostra o ecrã de login quando não há utilizador;
- mostra a app principal quando o utilizador está autenticado;
- controla a navegação entre ecrãs;
- guarda e carrega criações;
- liga a app ao armazenamento local e à cloud;
- controla a importação e exportação de ficheiros.

Um ponto importante é a variável `activeUser`, que guarda o utilizador atual. Se for `null`, a app mostra o `LoginScreen`. Se existir um utilizador, a app mostra o `Scaffold` principal com a barra de navegação inferior.

Também existe uma função chamada `saveCreations`, que guarda as equipas e estratégias atuais tanto localmente como na cloud. Isto evita repetir a mesma lógica em vários ecrãs.

O `LaunchedEffect(activeUser?.id)` é usado quando o utilizador muda. Quando alguém faz login, a app carrega primeiro os dados locais e depois tenta buscar os dados mais recentes da cloud. Se a cloud responder, os dados são atualizados e guardados de novo localmente.

A `MainActivity` também cria os launchers de importação e exportação, que usam o seletor de ficheiros do Android para abrir ou guardar ficheiros JSON.

## 6. Sistema de navegação

### `Screen.kt`

O ficheiro `Screen.kt` define todos os ecrãs da app e as suas rotas.

As rotas simples incluem:

- `home`
- `characters`
- `enemies`
- `items`
- `creations`

Também existem rotas com parâmetros, como:

- `character/{characterId}`
- `enemy/{enemyId}`
- `item/{itemId}`
- `team_detail/{teamId}`
- `strategy_detail/{strategyId}`

Isto permite abrir um ecrã de detalhe sabendo qual foi o item, personagem, inimigo, equipa ou estratégia selecionado.

No fim do ficheiro existe a lista `bottomNavItems`, que define os ecrãs que aparecem na barra de navegação inferior.

## 7. Modelos de dados

Os modelos de dados são as classes que representam aquilo que a app usa internamente.

### `Character.kt`

Este ficheiro define tudo o que faz parte de uma personagem.

Uma personagem tem:

- identificador;
- nome;
- descrição;
- vida;
- stamina;
- estabilidade;
- habilidade básica;
- habilidades normais;
- habilidade ultimate;
- passivas;
- efeitos.

Também existem classes auxiliares como `Skill`, `Passive` e `Effect`.

### `Enemy.kt`

Este ficheiro define os inimigos. Cada inimigo tem nome, descrição e uma lista de comportamentos.

Os comportamentos são representados pela classe `Behavior`, que tem um título e uma descrição. Isto permite explicar como cada inimigo atua em combate.

### `Item.kt`

Este ficheiro define os itens. Um item pode ser:

- ativo;
- passivo;
- ativo e passivo.

Isto é representado pelo `ItemType`. Cada item também tem uma descrição normal e uma descrição do seu efeito em combate.

### `Team.kt`

Este ficheiro define as equipas criadas pelo utilizador.

Uma equipa tem:

- id;
- nome;
- descrição;
- lista de membros;
- indicação se pode ser editada.

Cada membro da equipa é representado por `TeamMember`, que guarda o personagem escolhido, o item equipado e a iniciativa.

### `Strategy.kt`

Este ficheiro define uma estratégia.

Uma estratégia tem:

- id;
- nome;
- texto;
- lista de documentos referenciados;
- indicação se pode ser editada.

O texto da estratégia pode conter links internos para personagens, inimigos e itens, usando um formato parecido com Markdown.

## 8. Dados iniciais da app

### `MockData.kt`

O `MockData.kt` guarda os dados usados pela app enquanto ela está a correr.

Aqui existem listas fixas de:

- personagens;
- inimigos;
- itens.

Também existem listas mutáveis de:

- equipas;
- estratégias.

As listas fixas servem como base do Codex. As listas mutáveis são alteradas quando o utilizador cria, edita, importa ou apaga conteúdo.

Embora o nome seja `MockData`, este ficheiro acaba por funcionar como uma espécie de memória central da app. Muitos ecrãs vão buscar aqui a informação que precisam de mostrar.

## 9. Sistema de autenticação

### `FirebaseAuthRepository.kt`

Este ficheiro trata da autenticação com Firebase.

Foi criada a classe `LocalUser` para guardar apenas a informação que a app precisa sobre o utilizador: o id e o nome apresentado.

O objeto `FirebaseAuthRepository` tem quatro funções principais:

- `currentUser`: verifica se já existe uma sessão ativa.
- `login`: entra com email e palavra-passe.
- `register`: cria uma nova conta.
- `logout`: termina a sessão atual.

Uma vantagem desta abordagem é que o resto da app não precisa de lidar diretamente com o tipo `FirebaseUser`. O Firebase fica mais isolado dentro deste ficheiro.

## 10. Guardar dados localmente

### `LocalCreationsStorage.kt`

Este ficheiro trata de guardar as equipas e estratégias no armazenamento interno do dispositivo.

Cada utilizador tem o seu próprio ficheiro local. O nome do ficheiro é criado com base no id do utilizador, para evitar misturar dados de contas diferentes.

O ficheiro guardado tem formato JSON e inclui:

- versão do esquema;
- lista de equipas;
- lista de estratégias.

A função `load` carrega os dados do ficheiro local. Se o ficheiro não existir ou estiver inválido, a app limpa os dados em memória.

A função `save` escreve o estado atual das equipas e estratégias no ficheiro local.

Também existe a função `clearMemory`, que limpa apenas os dados em memória, sem apagar os ficheiros guardados no dispositivo.

Este sistema é importante porque permite que a app continue a funcionar mesmo sem rede.

## 11. Sincronização com a cloud

### `FirebaseCreationsDataSource.kt`

Este ficheiro trata da ligação ao Firestore.

Os dados são guardados na cloud com esta organização:

```text
users/{userId}/teams
users/{userId}/strategies
```

Ou seja, cada utilizador tem as suas próprias equipas e estratégias.

A função `loadUserCreations` carrega primeiro as equipas e depois as estratégias do utilizador. No final, devolve tudo junto através de `CloudCreations`.

A função `saveUserCreations` envia para a cloud o estado atual das equipas e estratégias. Ela também remove da cloud aquilo que foi apagado localmente, para manter os dados alinhados.

Como o Firestore trabalha com documentos e mapas de valores, este ficheiro também tem funções para converter:

- `Team` para mapa Firestore;
- `Strategy` para mapa Firestore;
- mapa Firestore para `Team`;
- mapa Firestore para `Strategy`;
- mapa Firestore para `TeamMember`.

## 12. Importação e exportação

### `CreationImportExport.kt`

Este ficheiro permite exportar e importar equipas e estratégias.

Os ficheiros gerados usam a extensão:

```text
.bpcodex.json
```

O JSON exportado guarda:

- `schemaVersion`, para saber a versão do formato;
- `kind`, para indicar se é uma equipa ou uma estratégia;
- `payload`, onde ficam os dados reais.

Existem funções de exportação para:

- `Team.toCodexExportJson`;
- `Strategy.toCodexExportJson`.

Também existem funções para gerar nomes de ficheiro seguros, removendo caracteres problemáticos.

Na importação, a função `importCodexCreation` valida a versão do ficheiro e o tipo de conteúdo. Depois cria uma equipa ou estratégia nova.

Um detalhe importante é que, ao importar, a app gera um novo id com base no tempo atual. Isto evita que uma importação substitua diretamente uma criação já existente com o mesmo id.

## 13. Ecrã de login e ecrã inicial

### `LoginScreen.kt`

Este ecrã permite ao utilizador entrar ou criar conta.

Tem dois campos:

- email;
- palavra-passe.

O botão só fica ativo quando o email não está vazio e a palavra-passe tem pelo menos seis caracteres. O ecrã também mostra mensagens de erro quando o Firebase devolve algum problema.

### `HomeScreen.kt`

O `HomeScreen` é o primeiro ecrã depois do login.

Mostra:

- saudação ao utilizador;
- botão de logout;
- banner visual da app;
- botões para criar nova equipa ou nova estratégia;
- equipas recentes;
- estratégias recentes.

Este ecrã também permite abrir detalhes de equipas, estratégias, personagens e outros elementos apresentados.

## 14. Consulta de personagens

### `CharactersScreen.kt`

Este ecrã mostra a lista de personagens do Codex.

Tem uma barra de pesquisa que filtra personagens pelo nome ou pela descrição. Cada personagem aparece num card com nome, descrição e atributos principais.

O ficheiro também contém componentes auxiliares como `CharacterCard` e `StatChip`.

### `CharacterDetailScreen.kt`

Este ecrã mostra a informação completa de uma personagem.

Inclui:

- descrição;
- atributos base;
- barras visuais de estatísticas;
- habilidade básica;
- habilidades normais;
- habilidade ultimate;
- passivas;
- efeitos.

Também existem componentes reutilizáveis como `StatBar` e `SkillCard`.

## 15. Consulta de inimigos

### `EnemiesScreen.kt`

Este ecrã lista os inimigos existentes no Codex.

Tal como nos personagens, existe uma barra de pesquisa. Cada inimigo aparece num card com nome, descrição e número de comportamentos.

### `EnemyDetailScreen.kt`

Este ecrã mostra os detalhes de um inimigo.

Mostra a descrição geral e a lista de comportamentos especiais. Cada comportamento aparece com título e descrição, para explicar melhor como o inimigo funciona.

## 16. Consulta de itens

### `ItemsScreen.kt`

Este ecrã lista os itens do Codex.

Inclui pesquisa por nome ou descrição. Cada item aparece com uma etiqueta que indica se é ativo, passivo, ou ativo/passivo.

### `ItemDetailScreen.kt`

Este ecrã mostra a informação completa de um item.

Inclui:

- tipo de item;
- descrição;
- efeito de combate.

As cores das etiquetas mudam conforme o tipo do item.

## 17. Criações do utilizador

### `CreationsScreen.kt`

Este ecrã organiza as criações do utilizador em duas abas:

- equipas;
- estratégias.

Permite:

- ver as criações existentes;
- abrir detalhes;
- editar;
- apagar;
- importar ficheiros;
- criar uma nova equipa ou estratégia através do botão flutuante.

Quando algo é apagado, o ecrã atualiza a lista e chama `onCreationsChanged`, que depois guarda as alterações.

### `TeamEditorScreen.kt`

Este ecrã permite criar ou editar uma equipa.

O utilizador pode:

- escrever o nome da equipa;
- escrever uma descrição;
- escolher até quatro personagens;
- escolher um item para cada personagem;
- definir a iniciativa de cada membro.

O ecrã usa dialogs de seleção para escolher personagens e itens. Também existe a opção de remover o item equipado ou limpar um slot.

Quando a equipa é guardada, ela é adicionada ou atualizada em `MockData.teams`.

### `TeamDetailScreen.kt`

Este ecrã mostra os detalhes de uma equipa.

Mostra:

- nome;
- descrição;
- membros;
- item equipado;
- iniciativa.

Também permite exportar a equipa e, se ela for editável, abrir o editor.

Os nomes dos personagens e itens podem ser clicados para abrir os respetivos detalhes no Codex.

### `StrategyEditorScreen.kt`

Este ecrã permite criar ou editar estratégias.

Tem duas abas:

- editar;
- pré-visualizar.

Na aba de edição, o utilizador escreve o nome e o texto da estratégia. Também pode inserir links para personagens, inimigos e itens através de botões.

Esses links usam este formato:

```text
[Nome Visível](doc:id)
```

Quando a estratégia é guardada, a app procura todos os `doc:id` no texto e guarda a lista de referências.

O ficheiro também contém a função `parseStrategyText`, que transforma esses links em texto clicável e destacado.

### `StrategyDetailScreen.kt`

Este ecrã mostra uma estratégia já guardada.

O texto é apresentado com os links internos formatados. Quando o utilizador clica num link, a app identifica o tipo pelo início do id:

- `c` para personagens;
- `e` para inimigos;
- `i` para itens.

Depois disso, navega para o ecrã de detalhe correspondente.

Este ecrã também permite editar e exportar a estratégia.

## 18. Tema visual

### `Color.kt`

Este ficheiro define as cores usadas na app.

A identidade visual da app usa um fundo escuro com cores neon, principalmente:

- indigo;
- cyan;
- verde;
- rosa.

Também existem cores para fundo, superfícies, cards e texto.

### `Theme.kt`

Este ficheiro aplica o tema da app.

O tema escuro é usado por defeito. Também são configuradas as cores da status bar e da navigation bar, para combinarem com o fundo escuro da aplicação.

### `Type.kt`

Este ficheiro define a tipografia usada pelo Material Theme. Mesmo que não tenha muita lógica, ajuda a manter a apresentação visual mais consistente.

## 19. Recursos visuais e Android

Na pasta `res` ficam os recursos usados pela aplicação.

Alguns exemplos:

- `drawable/background_codex_cortado.webp`: imagem de fundo usada globalmente.
- `mipmap`: ícones da aplicação em vários tamanhos.
- `values/strings.xml`: nome da app e textos globais.
- `values/themes.xml`: tema Android base.
- `xml/backup_rules.xml` e `xml/data_extraction_rules.xml`: configurações de backup e extração de dados.

O ficheiro `activity_main.xml` também existe, mas a interface real da app é feita em Compose, dentro do código Kotlin.

## 20. Testes

O projeto contém dois ficheiros de teste:

- `ExampleUnitTest.kt`
- `ExampleInstrumentedTest.kt`

Estes ficheiros são os testes base criados automaticamente pelo template Android. Neste momento ainda não testam as funcionalidades reais da aplicação.

No futuro, faria sentido acrescentar testes para:

- importação e exportação;
- leitura e escrita local;
- conversão de dados da cloud;
- validação dos editores;
- pesquisa nas listas.

## 21. Fluxos principais

### Login

O utilizador escreve email e palavra-passe. A app chama o Firebase. Se o login correr bem, o utilizador entra e os dados dele são carregados.

### Carregamento de dados

Depois do login, a app carrega primeiro os dados locais. Assim o utilizador consegue ver rapidamente as suas criações. Depois tenta carregar os dados da cloud e atualizar a memória.

### Criar equipa

O utilizador abre o editor, escolhe personagens, itens e iniciativas. Ao guardar, a equipa fica em memória, e depois é guardada localmente e na cloud.

### Criar estratégia

O utilizador escreve o texto e pode inserir links para documentos do Codex. Ao guardar, a app extrai as referências e guarda a estratégia.

### Exportar

Ao exportar uma equipa ou estratégia, a app transforma os dados em JSON e pede ao utilizador para escolher onde guardar o ficheiro.

### Importar

Ao importar, a app lê o ficheiro JSON, confirma se o formato é válido e adiciona a equipa ou estratégia às criações do utilizador.

## 22. Conclusão

O **Breaking Point Codex** é uma app que junta consulta, organização e criação de conteúdo num só lugar.

O projeto já tem vários sistemas importantes a funcionar: autenticação, navegação, dados locais, sincronização com cloud, importação/exportação e editores para conteúdo criado pelo utilizador.

Um dos pontos mais interessantes da app é o sistema de estratégias com links internos. Isto aproxima a app de um verdadeiro Codex, onde uma estratégia não é apenas texto solto, mas consegue apontar diretamente para personagens, inimigos e itens.

Como melhorias futuras, seria interessante separar alguma lógica da `MainActivity`, criar testes mais completos e melhorar o tratamento de erros. Ainda assim, a base da aplicação está bem definida e já permite usar a app de forma completa.
