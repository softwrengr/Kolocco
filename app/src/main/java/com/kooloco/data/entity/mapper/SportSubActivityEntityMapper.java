package com.kooloco.data.entity.mapper;

import com.kooloco.data.entity.SportSubActivityEntity;
import com.kooloco.model.SportSubActivity;

/**
 * Created by hlink44 on 26/4/17.
 */

public class SportSubActivityEntityMapper extends BaseMapper<SportSubActivityEntity, SportSubActivity> {

    @Override
    public SportSubActivity transform(SportSubActivityEntity sportSubActivityEntity) {
        if (sportSubActivityEntity != null) {
            SportSubActivity subService = new SportSubActivity();

            subService.setId(sportSubActivityEntity.getId());
            subService.setName(sportSubActivityEntity.getName());
            subService.setIcon(sportSubActivityEntity.getIcon());
            subService.setParentId(sportSubActivityEntity.getParentId());
            subService.setPrice(sportSubActivityEntity.getPrice());
            subService.setSelect(sportSubActivityEntity.getIsSelected().equalsIgnoreCase("1"));
            subService.setGroup(false);

            return subService;
        } else return null;
    }
}
