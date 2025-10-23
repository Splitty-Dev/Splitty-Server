package com.chaegangjo.goods.service;

import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.domain.GoodsImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GoodsImageService {

    @Transactional
    public List<GoodsImage> saveGoodsImages(Goods goods, List<String> imageNames) {
        for (int i = 1; i < imageNames.size(); i++) {
            String imageName = imageNames.get(i);
            GoodsImage image = new GoodsImage(goods, imageName);
            goods.addImage(image);
        }
        return goods.getImages();
    }
}
