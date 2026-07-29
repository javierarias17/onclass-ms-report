package co.com.pragma.api;

import co.com.pragma.api.dto.BootcampReportInDto;
import co.com.pragma.api.dto.BootcampReportOutDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IHandlerDocs {

    @Operation(
            operationId = "listenRegisterBootcampReport",
            summary = "Register a bootcamp report",
            description = "Persists a metrics snapshot for a bootcamp (its own data plus capability, technology "
                    + "and enrolled-person counts) into the report database. Called by bootcamp-ms every time a "
                    + "bootcamp is registered.",
            tags = { "Bootcamp Reports" },
            requestBody = @RequestBody(
                    description = "Input data",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BootcampReportInDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "bootcampId": 1,
                                      "name": "Java Backend Bootcamp",
                                      "description": "Bootcamp de backend con Java",
                                      "launchDate": "2026-08-01",
                                      "durationInWeeks": 12,
                                      "capabilityCount": 2,
                                      "technologyCount": 3,
                                      "enrolledPersonCount": 0
                                    }
                                    """))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BootcampReportOutDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "id": "665f1b2e8c1a2b3c4d5e6f70",
                                      "bootcampId": 1,
                                      "name": "Java Backend Bootcamp",
                                      "description": "Bootcamp de backend con Java",
                                      "launchDate": "2026-08-01",
                                      "durationInWeeks": 12,
                                      "capabilityCount": 2,
                                      "technologyCount": 3,
                                      "enrolledPersonCount": 0,
                                      "registeredAt": "2026-07-28T15:04:05Z"
                                    }
                                    """))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "message": "Business validation failed",
                                      "errors": [
                                        {
                                          "field": "bootcampId",
                                          "message": "Bootcamp id is required"
                                        }
                                      ]
                                    }
                                    """))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "message": "An unexpected error occurred. Please contact the administrator."
                                    }
                                    """)))
    })
    Mono<ServerResponse> listenRegisterBootcampReport(ServerRequest serverRequest);
}
