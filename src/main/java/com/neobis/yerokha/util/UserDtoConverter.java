package com.neobis.yerokha.util;

import com.neobis.yerokha.entity.user.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDtoConverter {

    public static void dtoToStatement(User dto, PreparedStatement statement) throws SQLException {

        statement.setString(1, dto.getFirstName());
        statement.setString(2, dto.getLastName());
        statement.setDate(3, dto.getBirthDate());
        statement.setString(4, dto.getEmail());
        statement.setString(5, dto.getPhoneNumber());
        statement.setString(6, dto.getPassword());
    }

    public static void resultSetToDto(ResultSet resultSet, User dto) throws SQLException {

        dto.setId(resultSet.getLong(1));
        dto.setFirstName(resultSet.getString(2));
        dto.setLastName(resultSet.getString(3));
        dto.setBirthDate(resultSet.getDate(4));
        dto.setEmail(resultSet.getString(5));
        dto.setPhoneNumber(resultSet.getString(6));
        dto.setPassword(resultSet.getString(7));
    }
}
