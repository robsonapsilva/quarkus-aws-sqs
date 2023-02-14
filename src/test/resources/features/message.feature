# coding: utf-8
# language: pt

Funcionalidade: MessageResource - Endpoint's para manipulacao de mensagens no SQS.

  Esquema do Cenário: BAD REQUEST <name>
    Quando Faco uma requisicao de envio de mensagem com o requestBody <requestBody>
    Entao Tenho codigo de retorno <statusCode>
    Exemplos: bad-request
      | name                              | requestBody                                | statusCode |
      | "[BAD REQUEST] - NAME"            | "message-bad-request-name.json"            | 400        |
      | "[BAD REQUEST] - ROTATION PERIOD" | "message-bad-request-rotation-period.json" | 400        |
      | "[BAD REQUEST] - ORBITAL PERIOD"  | "message-bad-request-orbital-period.json"  | 400        |
      | "[BAD REQUEST] - DIAMETER"        | "message-bad-request-diameter.json"        | 400        |
      | "[BAD REQUEST] - CLIMATE"         | "message-bad-request-climate.json"         | 400        |
      | "[BAD REQUEST] - GRAVITY"         | "message-bad-request-gravity.json"         | 400        |
      | "[BAD REQUEST] - TERRAIN"         | "message-bad-request-terrain.json"         | 400        |
      | "[BAD REQUEST] - SURFACE WATER"   | "message-bad-request-surface-water.json"   | 400        |
      | "[BAD REQUEST] - POPULATION"      | "message-bad-request-population.json"      | 400        |

  Esquema do Cenário: CREATED <name>
    Dado  Que a fila <queue> esta disponivel
    Quando Faco uma requisicao de envio de mensagem com o requestBody <requestBody>
    Entao Tenho codigo de retorno <statusCode>
    Exemplos: created
      | name                   | queue                   | requestBody                     | statusCode |
      | "[CREATED] - ALDERAAN" | "QUARKUS_AWS_SQS_QUEUE" | "message-success-alderaan.json" | 201        |
      | "[CREATED] - TATOOINE" | "QUARKUS_AWS_SQS_QUEUE" | "message-success-tatooine.json" | 201        |
      | "[CREATED] - YAVIN IV" | "QUARKUS_AWS_SQS_QUEUE" | "message-success-yavin-iv.json" | 201        |

  Esquema do Cenário: LIST <name>
    Dado  Que a fila <queue> esta disponivel
    Quando Faco uma requisicao para obter a lista de mensagem
    Entao Tenho codigo de retorno <statusCode>
    E Recuperou <messagesReceive> mensagens
    Exemplos: list
      | name                         | queue                   | statusCode | messagesReceive |
      | "[SUCCESS] - LIST MESSAGES " | "QUARKUS_AWS_SQS_QUEUE" | 200        | 3               |

