package com.company.dao;

import com.company.entity.AuthUser;
import com.company.entity.WeatherData;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Component
@Transactional
public class WeatherDao extends BaseDao<WeatherData, Long> {

    @Transactional(readOnly = true)
    public List<WeatherData> findAllBySubscribeLocation(AuthUser authUser, String weatherType) {
        LocalDate toDate = switch (weatherType) {
            case "month" -> LocalDate.now().plusMonths(1);
            case "week" -> LocalDate.now().plusWeeks(1);
            default -> LocalDate.now().plusDays(1);
        };
        try {
            return em.createQuery(
                            "select w from WeatherData w join w.location.subscribeUsers user " +
                                    "where user = :user and w.date between current_date and :toDate",
                            WeatherData.class)
                    .setParameter("user", authUser)
                    .setParameter("toDate", toDate)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
