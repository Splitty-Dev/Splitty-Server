package com.chaegangjo.member.utils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUsername {

    public static final List<String> ADJECTIVES = List.of(
            "빠른", "똑똑한", "귀여운", "늠름한", "장난꾸러기",
            "용감한", "느긋한", "멋진", "신비로운", "깜찍한",
            "활발한", "조용한", "영리한", "사려깊은", "명랑한",
            "강한", "섬세한", "유쾌한", "차분한", "당당한"
    );

    public static final List<String> ANIMALS = List.of(
            "토끼", "여우", "호랑이", "고양이", "강아지",
            "팬더", "사자", "부엉이", "코알라", "여우원숭이",
            "원숭이", "코끼리", "곰", "다람쥐", "말",
            "기린", "늑대", "펭귄", "수달", "치타"
    );

    public static String getRandomUsername() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        String randomAdjective = ADJECTIVES.get(random.nextInt(ADJECTIVES.size()));
        String randomAnimal = ANIMALS.get(random.nextInt(ANIMALS.size()));
        int randomNumber = random.nextInt(1001);

        return randomAdjective + randomAnimal + randomNumber;
    }
}
