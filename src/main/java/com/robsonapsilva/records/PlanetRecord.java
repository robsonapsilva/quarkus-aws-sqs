package com.robsonapsilva.records;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigInteger;

public record PlanetRecord(@NotBlank String name,
                           @Positive Integer rotationPeriod,
                           @Positive Integer orbitalPeriod,
                           @Positive Integer diameter,
                           @NotBlank String climate,
                           @NotBlank String gravity,
                           @NotBlank String terrain,
                           @NotNull @PositiveOrZero Integer surfaceWater,
                           @NotNull @PositiveOrZero BigInteger population) {
}
