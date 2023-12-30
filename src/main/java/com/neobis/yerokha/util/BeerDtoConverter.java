package com.neobis.yerokha.util;

import com.neobis.yerokha.entity.beer.Beer;
import com.neobis.yerokha.entity.beer.Container;
import com.neobis.yerokha.entity.beer.Style;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BeerDtoConverter {

    public static void dtoToStatement(Beer dto, PreparedStatement statement) throws SQLException {

        statement.setString(1, dto.getName());
        statement.setInt(2, dto.getStyle().equals(Style.ALE) ? 1 : 2);
        statement.setDouble(3, dto.getAlcohol());
        statement.setInt(4, dto.getContainer().equals(Container.CAN) ? 1 : 2);
        statement.setInt(5, dto.getSize());
        statement.setBigDecimal(6, dto.getPrice());
        statement.setString(7, dto.getCountry());
        statement.setInt(8, dto.getStockAmount());
    }

    public static void resultSetToDto(ResultSet resultSet, Beer dto) throws SQLException {
        dto.setId(resultSet.getLong(1));
        dto.setName(resultSet.getString(2));
        dto.setStyle(Style.valueOf(resultSet.getString(3)));
        dto.setAlcohol(resultSet.getDouble(4));
        dto.setContainer(Container.valueOf(resultSet.getString(5)));
        dto.setSize(resultSet.getInt(6));
        dto.setPrice(resultSet.getBigDecimal(7));
        dto.setCountry(resultSet.getString(8));
        dto.setStockAmount(resultSet.getInt(9));

    }

}
