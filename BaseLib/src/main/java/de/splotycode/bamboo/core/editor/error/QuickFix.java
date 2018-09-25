package de.splotycode.bamboo.core.editor.error;

import de.splotycode.bamboo.core.data.DataFactory;
import de.splotycode.bamboo.core.data.DataKey;
import de.splotycode.bamboo.core.i18n.I18N;

public abstract class QuickFix {

    private DataFactory dataFactory;

    public QuickFix.FactoryBuilder factoryBuilder() {
        dataFactory = new DataFactory();
        return new QuickFix.FactoryBuilder();
    }

    public QuickFix.FactoryBuilder factoryBuilder(DataFactory dataFactory) {
        this.dataFactory = dataFactory;
        return new QuickFix.FactoryBuilder();
    }

    public class FactoryBuilder {

        public <T> QuickFix.FactoryBuilder addData(DataKey<T> key, T data) {
            dataFactory.putData(key, data);
            return this;
        }

        public QuickFix build() {
            return QuickFix.this;
        }

    }

    public abstract void fix();
    public abstract String getName();

    public String getDisplayNamePath() {
        return "quickfixes." + getName() + ".name";
    }

    public String getDisplayName() {
        return I18N.get(getDisplayNamePath());
    }

    public String getDescriptionPath() {
        return "quickfixes." + getName() + ".description";
    }

    public String getDescription() {
        return I18N.get(getDescriptionPath());
    }

}
