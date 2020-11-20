package com.linka39.genyundemo.entity;

import java.util.List;

/**
 * 树节点的数据结构
 */
public class TreeLevel {
    private Integer currentIndex; // 当前处理的目录集合的索引
    private List<String> levelCatalog; // 遍历层次的所有目录的节点名称

    public TreeLevel() {
    }
    public TreeLevel(Integer currentIndex, List<String> levelCatalog) {
        this.currentIndex = currentIndex;
        this.levelCatalog = levelCatalog;
    }

    public Integer getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(Integer currentIndex) {
        this.currentIndex = currentIndex;
    }

    public List<String> getLevelCatalog() {
        return levelCatalog;
    }

    public void setLevelCatalog(List<String> levelCatalog) {
        this.levelCatalog = levelCatalog;
    }
}
