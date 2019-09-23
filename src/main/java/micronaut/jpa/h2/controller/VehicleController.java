package micronaut.jpa.h2.controller;


import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.multipart.CompletedFileUpload;
import micronaut.jpa.h2.model.Vehicle;
import micronaut.jpa.h2.model.VehicleCvsDTO;
import micronaut.jpa.h2.model.VehicleJSONDTO;
import micronaut.jpa.h2.service.VehicleService;
import micronaut.jpa.h2.util.csv.CSVParserUtil;
import micronaut.jpa.h2.util.csv.CsvBean;
import micronaut.jpa.h2.util.error.ErrorEnum;
import micronaut.jpa.h2.util.response.ResponseData;
import micronaut.jpa.h2.util.response.ResponseError;

import javax.inject.Inject;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * A REST controller to create vehicle entries
 */
@Controller
public class VehicleController {

    @Inject
    private VehicleService vehicleService;

    /**
     * Receives a list of vehicles along side dealerId
     *
     * @param dealerId    the dealer whose vehicles to be saved
     * @param vehicleDTOs vehicles
     * @return
     */
    @Post("/v1/vehicle_listings/{dealerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse saveOrUpdateListingJSON(@PathVariable("dealerId") Long dealerId, @NotEmpty @Body List<VehicleJSONDTO> vehicleDTOs) {

        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleDTOs.forEach(s -> {
            s.setDealerId(dealerId);
            vehicleList.add(s.toVehicleEntity());
        });

        try {
            List<VehicleJSONDTO> vehicleListResp = new ArrayList<>();
            vehicleService.saveOrUpdateAll(vehicleList).forEach(s -> vehicleListResp.add(VehicleJSONDTO.from(s)));
            return HttpResponse.ok(ResponseData.builder().data(vehicleListResp).build());
        } catch (RuntimeException e) {
            e.printStackTrace();
            return HttpResponse.badRequest().body(ResponseError.builder().code(ErrorEnum.INVALID_DATA.getCode()).message(ErrorEnum.INVALID_DATA.getMessage()).detail(e.getMessage()).build());
        }
    }

    /**
     * Receives a csv file and parses contents. Then saves contents with dealerId
     *
     * @param dealerId
     * @param file
     * @return
     */
    @Post("/v1/upload_csv/{dealerId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse saveOrUpdateListingCSV(@PathVariable("dealerId") Long dealerId, @NotNull CompletedFileUpload file) {//@Body(value = "file") @NotNull MultipartFile file) {
        try {
            List<CsvBean> csvList = CSVParserUtil.parse(file.getInputStream(), VehicleCvsDTO.class);
            List<Vehicle> vehicleList = new ArrayList<>();
            csvList.forEach(s -> {
                ((VehicleCvsDTO) s).setDealerId(dealerId);
                vehicleList.add(((VehicleCvsDTO) s).toVehicleEntity());
            });
            List<VehicleJSONDTO> vehicleListResp = new ArrayList<>();
            vehicleService.saveOrUpdateAll(vehicleList).forEach(s -> vehicleListResp.add(VehicleJSONDTO.from(s)));
            return HttpResponse.ok(ResponseData.builder().data(vehicleListResp).build());
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.badRequest().body(ResponseError.builder().code(ErrorEnum.INVALID_DATA.getCode()).message(ErrorEnum.INVALID_DATA.getMessage()).detail(e.getMessage()).build());
        }
    }

}
