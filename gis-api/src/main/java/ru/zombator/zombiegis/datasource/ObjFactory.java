package ru.zombator.zombiegis.datasource;

import java.util.Collection;

import ru.zombator.zombiegis.model.obj.Obj;
import ru.zombator.zombiegis.transfers.ObjDTO;

/**
 * Фабрика объектов предметной области. Создает объекты предметной области по DTO.
 * <p>
 * Чтобы получить экземпляр ObjFactory:
 * <blockquote>
 * Lookup.getDefault().lookup(ObjFactory.class)
 * </blockquote>
 * <p>
 * Стандартная реализация фабрики объектов предметной области находит 
 *
 * @author nvamelichev
 */
public interface ObjFactory {
    /**
     * Конвертирует указанные DTO в объекты предметной области.
     *
     * @param dtos DTO, конвертируемые в объекты предметной области
     *
     * @return все успешно сконвертированные DTO
     */
    Collection<Obj<?, ?>> toDomain(Collection<ObjDTO> dtos);
}
