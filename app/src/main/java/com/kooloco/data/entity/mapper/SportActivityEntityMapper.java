package com.kooloco.data.entity.mapper;

import com.kooloco.data.entity.SportActivityEntity;
import com.kooloco.model.SportActivity;

/**
 * Created by hlink44 on 21/11/17.
 */

public class SportActivityEntityMapper extends BaseMapper<SportActivityEntity, SportActivity> {
    @Override
    public SportActivity transform(SportActivityEntity sportActivityEntity) {
        if (sportActivityEntity != null) {
            SportActivity subService = new SportActivity();

            subService.setId(sportActivityEntity.getId());
            subService.setName(sportActivityEntity.getName());
            subService.setIcon(sportActivityEntity.getIcon());
            subService.setParentId(sportActivityEntity.getParentId());
            subService.setPrice(sportActivityEntity.getPrice());

            subService.setSelect(sportActivityEntity.getIsSelected().equalsIgnoreCase("1"));
            subService.setExpand(sportActivityEntity.getIsExpandable().equalsIgnoreCase("1"));
            subService.setGroup(false);
            subService.setSportSubActivities(new SportSubActivityEntityMapper().transformCollection(sportActivityEntity.getSubSportType()));

            return subService;
        } else return null;
    }
}
