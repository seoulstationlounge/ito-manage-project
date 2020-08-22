package com.itosideproject.modules.account;

import com.querydsl.core.types.Predicate;
import com.itosideproject.modules.tag.Tag;
import com.itosideproject.modules.zone.Zone;

import java.util.Set;


public class AccountPredicates {

    public static Predicate findByTagsAndZones(Set<Tag> tags, Set<Zone> zones) {
        com.itosideproject.modules.account.QAccount account = com.itosideproject.modules.account.QAccount.account;
        return account.zones.any().in(zones).and(account.tags.any().in(tags));
    }

}
