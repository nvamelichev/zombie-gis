package ru.zombator.zombiegis.properties;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

/**
 * Значение свойства "тип объекта карты".
 *
 * @author nvamelichev
 */
public enum Type {
    /**
     * Любой объект карты.
     */
    ANY,
    /**
     * Подвижный объект карты, например, корабль.
     */
    MOBILE(Type.ANY),
    /**
     * Полигон или полилиния.
     */
    POLY(Type.ANY),
    /**
     * Дуга эллипса.
     */
    ARC(Type.ANY),
    /**
     * Подпись.
     */
    LABEL(Type.ANY),
    /**
     * Пометка места на карте "иголкой".
     */
    PIN(Type.ANY),
    /**
     * Корабль.
     */
    SHIP(Type.MOBILE);

    private final Set<Type> parents;

    private Type(Type... parents) {
        this.parents = ImmutableSet.copyOf(parents);
    }

    /**
     * Возвращает непосредственные родительские типы данного типа.
     *
     * @return непосредственные родительские типы
     */
    public Set<Type> getParents() {
        return parents;
    }

    /**
     * Возвращает все родительские типы данного типа.
     *
     * @return все родительские типы
     */
    public Set<Type> getAllParents() {
        Set<Type> allParents = EnumSet.noneOf(Type.class);

        Deque<Type> stack = new ArrayDeque<>();
        stack.push(this);

        while (!stack.isEmpty()) {
            Type t = stack.pop();
            allParents.addAll(t.parents);
            for (Type parent: t.parents) {
                stack.push(parent);
            }
        }

        return allParents;
    }

    /**
     * Проверяет, является ли данный тип подтипом указанного типа объектов.
     *
     * @param parent родительский тип
     *
     * @return <code>true</code>, если данный тип равен <code>type</code> или является его подтипом
     */
    public boolean isSubtypeOf(Type parent) {
        return this == parent || getAllParents().contains(parent);
    }
}
