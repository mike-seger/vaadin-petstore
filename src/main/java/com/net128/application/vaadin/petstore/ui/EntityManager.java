package com.net128.application.vaadin.petstore.ui;

import com.net128.application.vaadin.petstore.model.Identifiable;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.List;

@SpringComponent
@UIScope
public abstract class EntityManager<T extends Identifiable> extends WorkingArea {
    private final EntityEditor<T> entityEditor;
    private final Grid<T> grid;

    public EntityManager(EntityEditor<T> entityEditor) {
        this.entityEditor = entityEditor;
        grid = new Grid<>(entityEditor.getTypeParameterClass());
    }

    public void layout() {
        setSizeFull();
        grid.setColumnReorderingAllowed(true);

        UI.getCurrent().addShortcutListener(u -> entityEditor.editNew(), Key.KEY_N,
            KeyModifier.CONTROL, KeyModifier.ALT);

        grid.asSingleSelect().addValueChangeListener(
            e -> entityEditor.edit(e.getValue()));

        HorizontalLayout masterDetail = new HorizontalLayout(grid, entityEditor);
        masterDetail.setSizeFull();
        masterDetail.setHeight("400px");
        entityEditor.setWidth("400px");
        entityEditor.setChangeHandler(() -> {
            entityEditor.setVisible(false);
            grid.setItems(listEntities());
        });

        setupGrid(grid);
        HorizontalLayout actionBar = createActionBar(entityEditor);
        actionBar.setMargin(true);
        add(actionBar, masterDetail);
        grid.setItems(listEntities());
        grid.setSizeFull();
    }

    protected void setGridData(List<T> entities) {
        grid.setItems(listEntities());
        grid.setSizeFull();
    }

    public abstract void setupGrid(Grid<T> grid);
    public abstract HorizontalLayout createActionBar(EntityEditor<T> entityEditor);
    public abstract List<T> listEntities();
}
