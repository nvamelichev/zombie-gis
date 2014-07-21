package ru.zombator.zombiegis.model.obj.shape.creator;

import static ru.zombator.zombiegis.datasource.TypedProp.*;

import ru.zombator.zombiegis.datasource.PropValues;
import ru.zombator.zombiegis.datasource.spi.ObjCreator;
import ru.zombator.zombiegis.datasource.spi.def.DefObjCreator;
import ru.zombator.zombiegis.model.obj.def.BasicDataModel;
import ru.zombator.zombiegis.model.obj.def.BasicViewModel;
import ru.zombator.zombiegis.model.obj.shape.PolyData;
import ru.zombator.zombiegis.model.obj.shape.PolyView;
import ru.zombator.zombiegis.properties.Type;

/**
 * @author nvamelichev
 */
@ObjCreator.Registration(type = Type.POLY)
public final class PolyObjCreator extends DefObjCreator<PolyData.Builder, PolyView.Builder> {
    @Override
    protected PolyData.Builder dataBuilder() {
        return PolyData.builder();
    }

    @Override
    protected PolyView.Builder viewBuilder() {
        return PolyView.builder();
    }

    @Override
    protected BasicDataModel.Builder fillData(PolyData.Builder bldr, PropValues props) {
        return super.fillData(bldr
                .points(props.getAll(POLY_POINTS)), props);
    }

    @Override
    protected BasicViewModel.Builder fillView(PolyView.Builder bldr, PropValues props) {
        return super.fillView(bldr
                .closed(props.get(POLY_CLOSED)), props);
    }
}
