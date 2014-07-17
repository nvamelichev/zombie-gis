package ru.zombator.zombiegis.model.obj.util;

import java.util.Collection;
import java.util.Set;

import org.netbeans.api.annotations.common.NonNull;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;

import ru.zombator.zombiegis.model.obj.Obj;
import ru.zombator.zombiegis.properties.Type;

/**
 * Утилиты для фильтрации списка объектов.
 *
 * @author anv
 *
 * @see ru.zombator.zombiegis.model.ObjMap#objs(Predicate) ObjMap.objs(Predicate&lt;Obj&lt;?, ?>)
 */
public final class Objs {
    private Objs() {
    }

    /**
     * Фильтр для возвращения всех объектов.
     */
    public static Predicate<Obj<?, ?>> all() {
        return Predicates.alwaysTrue();
    }

    /**
     * Фильтр, не возвращающий ни одного объекта.
     */
    public static Predicate<Obj<?, ?>> none() {
        return Predicates.alwaysFalse();
    }

    /**
     * Фильтр для возвращения объектов указанного типа.
     *
     * @param type тип объектов
     *
     * @return фильтр
     *
     * @throws NullPointerException <code>type == null</code>
     */
    public static Predicate<Obj<?, ?>> ofType(@NonNull Type type) {
        return ofTypes(type);
    }

    /**
     * Фильтр для возвращения объектов, имеющих хотя бы один из указанных типов.
     *
     * @param types типы объектов
     *
     * @return фильтр
     *
     * @throws NullPointerException <code>types == null || Arrays.asList(types).contains(null)</code>
     */
    public static Predicate<Obj<?, ?>> ofTypes(@NonNull Type... types) {
        if (types.length == 0) {
            return Predicates.alwaysTrue();
        }

        final Set<Type> acceptableTypes = ImmutableSet.copyOf(types);
        return new Predicate<Obj<?, ?>>() {
            @Override
            public boolean apply(Obj<?, ?> obj) {
                for (Type type: acceptableTypes) {
                    if (obj.getType().isSubtypeOf(type)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    /**
     * Фильтр для возвращения только подвижных объектов.
     *
     * @return фильтр, возвращающий только подвижные объекты
     *
     * @see Obj#isMobile()
     */
    public static Predicate<Obj<?, ?>> mobile() {
        return mobile(true);
    }

    /**
     * Фильтр для возвращения только неподвижных объектов.
     *
     * @return фильтр, возвращающий только неподвижные объекты
     *
     * @see Obj#isMobile()
     */
    public static Predicate<Obj<?, ?>> immobile() {
        return mobile(false);
    }

    /**
     * Фильтр для возвращения только подвижных или только неподвижных объектов.
     *
     * @param isMobile <code>true</code>: возвращать только подвижные объекты;
     *                 <code>false</code>: возвращать только неподвижные объекты
     *
     * @return фильтр, возвращающий только подвижные или только неподвижные объекты
     *
     * @see Obj#isMobile()
     */
    public static Predicate<Obj<?, ?>> mobile(final boolean isMobile) {
        return new Predicate<Obj<?, ?>>() {
            @Override
            public boolean apply(Obj<?, ?> obj) {
                return obj.isMobile() == isMobile;
            }
        };
    }

    /**
     * Фильтр для возвращения всех объектов, совпадающие с указанными.
     *
     * @param objs объекты, которые фильтр пропускает
     *
     * @return фильтр, пропускающий только указанные объекты
     */
    public static Predicate<Obj<?, ?>> allOf(Collection<? extends Obj<?, ?>> objs) {
        final Set<Obj<?, ?>> objSet = ImmutableSet.copyOf(objs);
        return new Predicate<Obj<?, ?>>() {
            @Override
            public boolean apply(Obj<?, ?> obj) {
                return objSet.contains(obj);
            }
        };
    }

    /**
     * Фильтр для возвращения всех объектов, не совпадающих с указанными.
     *
     * @param objs объекты, которые фильтр исключает
     *
     * @return фильтр, исключающий только указанные объекты
     */
    public static Predicate<Obj<?, ?>> noneOf(Collection<? extends Obj<?, ?>> objs) {
        return Predicates.not(allOf(objs));
    }
}
