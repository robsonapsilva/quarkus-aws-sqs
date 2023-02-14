package com.robsonapsilva.stepdefs;

import com.robsonapsilva.resource.SQSResource;
import com.robsonapsilva.utils.FileUtils;
import com.robsonapsilva.records.MessageRecord;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.quarkus.test.common.QuarkusTestResource;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

@QuarkusTestResource(SQSResource.class)
@RequiredArgsConstructor
public class MessageStepDef {

    private static final String PATH_MOCK_MESSAGES = "mock-messages/";
    private final SqsClient sqsClient;

    private Response response;

    @Dado("Que a fila {string} esta disponivel")
    public void que_a_fila_esta_disponivel(String queueName) {
        sqsClient.createQueue(queue -> queue.queueName(queueName));
    }

    @Quando("Faco uma requisicao de envio de mensagem com o requestBody {string}")
    public void faco_uma_requisicao_de_envio_de_mensagem_com_o_request_body(String fileName) {
        response = given()
                .header("Content-type", "application/json")
                .and()
                .body(FileUtils.readFile(PATH_MOCK_MESSAGES.concat(fileName)))
                .when()
                .post("/v1/messages")
                .thenReturn();
    }

    @Quando("Faco uma requisicao para obter a lista de mensagem")
    public void faco_uma_requisicao_para_obter_a_lista_de_mensagem() {
        response = given()
                .when()
                .get("/v1/messages")
                .thenReturn();
    }

    @Entao("Tenho codigo de retorno {int}")
    public void tenho_codigo_de_retorno(Integer statusCode) {
        Assert.assertTrue(statusCode.equals(response.statusCode()));
    }

    @Entao("Recuperou {int} mensagens")
    public void recuperou_mensagens(Integer size) {
        List<MessageRecord> messages = new ArrayList<>();
        messages = response.body().as(messages.getClass());
        Assert.assertTrue(size.equals(messages.size()));
    }

}

