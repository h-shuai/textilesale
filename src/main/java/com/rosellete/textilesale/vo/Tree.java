package com.rosellete.textilesale.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Tree<T> extends DepartmentVO {
    private List<Tree<T>> children = new ArrayList<Tree<T>>();//节点的子节点
}
