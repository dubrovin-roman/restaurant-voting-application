package ru.javaops.bootjava.to;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.javaops.bootjava.HasId;
import ru.javaops.bootjava.validation.NoHtml;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends NamedTo implements HasId {
    @NotBlank
    @Size(min = 5, max = 512)
    @NoHtml
    String address;

    public RestaurantTo(Integer id, String name, String address) {
        super(id, name);
        this.address = address;
    }
}
