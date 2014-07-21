package ru.zombator.zombiegis.model.obj.shape.creator;

import static ru.zombator.zombiegis.datasource.TypedProp.*;

import ru.zombator.zombiegis.datasource.PropValues;
import ru.zombator.zombiegis.datasource.spi.ObjCreator;
import ru.zombator.zombiegis.datasource.spi.def.DefObjCreator;
import ru.zombator.zombiegis.model.obj.def.BasicDataModel;
import ru.zombator.zombiegis.model.obj.def.BasicViewModel;
import ru.zombator.zombiegis.model.obj.shape.LabelView;
import ru.zombator.zombiegis.properties.Type;

/**
 * @author nvamelichev
 */
@ObjCreator.Registration(type = Type.LABEL)
public final class LabelObjCreator extends DefObjCreator<BasicDataModel.Builder, LabelView.Builder> {
    @Override
    protected BasicDataModel.Builder dataBuilder() {
        return BasicDataModel.builder();
    }

    @Override
    protected LabelView.Builder viewBuilder() {
        return LabelView.builder();
    }

    @Override
    protected BasicViewModel.Builder fillView(LabelView.Builder bldr, PropValues props) {
        return super.fillView(bldr
                .text(props.get(LABEL_TEXT))
                .font(props.get(LABEL_FONT_NAME))
                .size(props.get(LABEL_FONT_SIZE))
                .styles(props.getAll(LABEL_TEXT_STYLE))
                .align(props.get(LABEL_TEXT_ALIGN)), props);
    }
}
