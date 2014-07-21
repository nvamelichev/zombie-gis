package ru.zombator.zombiegis.model.obj.shape.creator;

import static ru.zombator.zombiegis.datasource.TypedProp.*;

import ru.zombator.zombiegis.datasource.PropValues;
import ru.zombator.zombiegis.datasource.spi.ObjCreator;
import ru.zombator.zombiegis.datasource.spi.def.DefObjCreator;
import ru.zombator.zombiegis.model.obj.def.BasicDataModel;
import ru.zombator.zombiegis.model.obj.def.BasicViewModel;
import ru.zombator.zombiegis.model.obj.shape.ArcView;
import ru.zombator.zombiegis.properties.Type;

/**
 * @author nvamelichev
 */
@ObjCreator.Registration(type = Type.ARC)
public final class ArcObjCreator extends DefObjCreator<BasicDataModel.Builder, ArcView.Builder> {
    @Override
    protected BasicDataModel.Builder dataBuilder() {
        return BasicDataModel.builder();
    }

    @Override
    protected ArcView.Builder viewBuilder() {
        return ArcView.builder();
    }

    @Override
    protected BasicViewModel.Builder fillView(ArcView.Builder bldr, PropValues props) {
        return super.fillView(bldr
                .startAt(props.get(ARC_START))
                .extent(props.get(ARC_EXTENT))
                .xRadius(props.get(ARC_R_X))
                .yRadius(props.get(ARC_R_Y)), props);
    }
}
