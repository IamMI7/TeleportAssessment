package teleport.assessment.service;

import org.springframework.stereotype.Service;
import teleport.assessment.dto.NextTrackingNumberResponse;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class TrackingNumberServiceImpl implements TrackingNumberService {
    @Override
    public NextTrackingNumberResponse generateTrackingNumber(String originCountryId, String destinationCountryId, BigDecimal weight, ZonedDateTime createdAt, UUID customerId, String customerName, String customerSlug) {
        String input = customerId.toString() + createdAt.toInstant().toString();
        String trackingNumber = sha256ToBase36(input);

        return new NextTrackingNumberResponse(
                trackingNumber,
                ZonedDateTime.now(),
                originCountryId,
                destinationCountryId,
                weight,
                createdAt,
                customerId,
                customerName,
                customerSlug,
                null
        );
    }


    private String sha256ToBase36(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            BigInteger bigInt = new BigInteger(1, hash);
            String base36 = bigInt.toString(36).toUpperCase();

            // Truncate or pad to 16 characters (regex: ^[A-Z0-9]{1,16}$)
            if (base36.length() > 16) {
                base36 = base36.substring(0, 16);
            } else {
                base36 = String.format("%16s", base36).replace(' ', '0');
            }
            return base36;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
}
