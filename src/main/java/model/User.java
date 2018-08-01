package model;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class User {

    private int id;

    private String firstName;

    private String lastName;

    private Date birth;

    private Address address;


}
