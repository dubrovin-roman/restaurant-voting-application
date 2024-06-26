package ru.javaops.bootjava.web.restaurant;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javaops.bootjava.model.Restaurant;
import ru.javaops.bootjava.repository.RestaurantRepository;

@Component
@AllArgsConstructor
public class UniqueAddressValidator implements Validator {
    public static final String EXCEPTION_DUPLICATE_ADDRESS = "Restaurant address already exists";

    private final RestaurantRepository restaurantRepository;
    private final HttpServletRequest request;

    @Override
    public boolean supports(Class<?> clazz) {
        return Restaurant.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Restaurant restaurantTarget = (Restaurant) target;
        if (StringUtils.hasText(restaurantTarget.getAddress())) {
            restaurantRepository.findRestaurantByAddressIgnoreCase(restaurantTarget.getAddress().trim())
                    .ifPresent(restaurant -> {
                        if (request.getMethod().equals("PUT")) {
                            String requestURI = request.getRequestURI();
                            int id = Integer.parseInt(requestURI.substring(requestURI.lastIndexOf("/") + 1));
                            if (id == restaurant.id()) {
                                return;
                            }
                        }
                        errors.rejectValue("address", "", EXCEPTION_DUPLICATE_ADDRESS);
                    });
        }
    }
}
