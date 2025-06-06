package teleport.assessment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class NextTrackingNumberResponse {
    // Referring to the generated unique tracking number
    @JsonProperty("tracking_number")
    private String trackingNumber;
    // Referring to the timestamp where the tracking number is created instead of the timestamp when the order is created
    @JsonProperty("created_at")
    private ZonedDateTime createdAt;
    @JsonProperty("origin_country_id")
    private String originCountryId;
    @JsonProperty("destination_country_id")
    private String destinationCountryId;
    @JsonProperty("weight")
    private BigDecimal weight;
    // Referring to the timestamp when the order was created passed in by the API Request
    @JsonProperty("order_created_at")
    private ZonedDateTime orderCreatedAt;
    @JsonProperty("customer_id")
    private UUID customerId;
    @JsonProperty("customer_name")
    private String customerName;
    @JsonProperty("customer_slug")
    private String customerSlug;
    // This field will only not be null if an exception is hit and a brief description of the error is passed in this field.
    @JsonProperty("error_message")
    private String errorMessage;
}
