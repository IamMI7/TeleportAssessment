package teleport.assessment.service;

import teleport.assessment.dto.NextTrackingNumberResponse;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

public interface TrackingNumberService {
    NextTrackingNumberResponse generateTrackingNumber(String originCountryId, String destinationCountryId, BigDecimal weight, ZonedDateTime createdAt, UUID customerId, String customerName, String customerSlug);
}
