package micronaut.jpa.h2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.client.multipart.MultipartBody;
import io.micronaut.test.annotation.MicronautTest;
import micronaut.jpa.h2.model.VehicleJSONDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class VehicleControllerTest {

    @Inject
    @Client("/v1")
    HttpClient client;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void saveOrUpdateJSONVehicles_whenGivenValidVehicles_callServiceSaveOrUpdate() throws Exception {

        VehicleJSONDTO invalidVehicleDTO = VehicleJSONDTO.builder().code("a").make("Honda").model("Integra").year(1996).kW(190).color("white").price(40000.45).build();
        List<VehicleJSONDTO> invalidList = new ArrayList<>();
        invalidList.add(invalidVehicleDTO);
        String jsonVal = mapper.writeValueAsString(invalidList);

        HttpRequest<String> request = HttpRequest.POST("/vehicle_listings/1", jsonVal);
        HttpResponse body = client.toBlocking().exchange(request);
        assertEquals(body.getStatus(), HttpResponse.ok().getStatus());
    }

    @Test
    public void saveOrUpdateJSONVehicles_whenGivenInvalidVehicles_returnsBadRequest() throws JsonProcessingException {

        VehicleJSONDTO invalidVehicleDTO = VehicleJSONDTO.builder().code("").make("Honda").model("Integra").year(1996).kW(190).color("white").price(40000.45).build();
        List<VehicleJSONDTO> invalidList = new ArrayList<>();
        invalidList.add(invalidVehicleDTO);
        String jsonVal = mapper.writeValueAsString(invalidList);

        HttpRequest<String> request = HttpRequest.POST("/vehicle_listings/1", jsonVal);
        HttpClientResponseException e =  Assertions.assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking().exchange(request);
        });

        assertEquals(e.getStatus(), HttpResponse.badRequest().getStatus());

    }


    @Test
    public void saveOrUpdateCSVVehicles_whenGivenValidCsv_callServiceSaveOrUpdate() throws Exception {

        File f = new File("src/test/java/resources/vehicles.csv");
        MultipartBody multipartBody = MultipartBody
                .builder()
                .addPart("file", "vehicles.csv", new MediaType(MediaType.MULTIPART_FORM_DATA), f)
                .build();

        HttpResponse response = client.toBlocking()
                .exchange(HttpRequest.POST("/upload_csv/1", multipartBody).contentType(MediaType.MULTIPART_FORM_DATA_TYPE));

        assertEquals(response.getStatus(), HttpResponse.ok().getStatus());
    }


    @Test
    public void saveOrUpdateCSVVehicles_whenGivenInvalidCsv_returnsBadRequest() {
        File f = new File("src/test/java/resources/vehiclesInvalid.csv");
        MultipartBody multipartBody = MultipartBody
                .builder()
                .addPart("file", "vehicles.csv", new MediaType(MediaType.MULTIPART_FORM_DATA), f)
                .build();

        HttpClientResponseException e = Assertions.assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking()
                    .exchange(HttpRequest.POST("/upload_csv/1", multipartBody).contentType(MediaType.MULTIPART_FORM_DATA_TYPE));
        });

        assertEquals(e.getStatus(), HttpResponse.badRequest().getStatus());

    }


}