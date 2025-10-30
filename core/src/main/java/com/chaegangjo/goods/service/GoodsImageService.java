package com.chaegangjo.goods.service;

import com.chaegangjo.goods.domain.Goods;
import com.chaegangjo.goods.domain.GoodsImage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GoodsImageService {

    @Transactional
    public List<GoodsImage> saveGoodsImages(Goods goods, List<String> imageNames) {
        imageNames.forEach(imageName -> {
                    GoodsImage image = new GoodsImage(goods, imageName);
                    goods.addImage(image);
        });
        return goods.getImages();
    }
}
