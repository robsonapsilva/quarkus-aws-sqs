package com.robsonapsilva.resources;

import com.robsonapsilva.records.MessageRecord;
import com.robsonapsilva.records.PlanetRecord;
import com.robsonapsilva.service.SQSClientService;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/v1/messages")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {

    private final SQSClientService sqsClientService;

    @GET
    public Response receive() {
        List<MessageRecord> messageRecords = sqsClientService.receive();
        return Response.ok().entity(messageRecords).build();
    }

    @POST
    public Response send(@Valid PlanetRecord planet) {
        MessageRecord messageRecord = sqsClientService.send(planet);
        return Response.created(URI.create(messageRecord.messageId()))
                .build();
    }

}
