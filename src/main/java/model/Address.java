package model;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Address {

    private int id;

    private String city;

    private String street;

    private int house;

    private int userId;
}
