package teleport.assessment.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import teleport.assessment.dto.NextTrackingNumberResponse;
import teleport.assessment.service.TrackingNumberService;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/next-tracking-number")
public class TrackingNumberController {

    private final TrackingNumberService trackingNumberService;

    public TrackingNumberController(TrackingNumberService trackingNumberService) {
        this.trackingNumberService = trackingNumberService;
    }

    @GetMapping
    public ResponseEntity<NextTrackingNumberResponse> getNextTrackingNumber(
            @RequestParam("origin_country_id") String originCountryId,
            @RequestParam("destination_country_id") String destinationCountryId,
            @RequestParam("weight") BigDecimal weight,
            @RequestParam("created_at") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime createdAt,
            @RequestParam("customer_id") UUID customerId,
            @RequestParam("customer_name") String customerName,
            @RequestParam("customer_slug") String customerSlug
    ) {
        if (weight.scale() > 3) {
            return ResponseEntity.badRequest()
                    .body(new NextTrackingNumberResponse(null, null, originCountryId, destinationCountryId, weight, createdAt, customerId, customerName, customerSlug, "Weight cannot exceed 3 DP"));
        }
        return ResponseEntity.ok(trackingNumberService.generateTrackingNumber(originCountryId, destinationCountryId, weight, createdAt, customerId, customerName, customerSlug));
    }
}
