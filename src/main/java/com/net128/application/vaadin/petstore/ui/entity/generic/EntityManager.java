package com.net128.application.vaadin.petstore.ui.entity.generic;

import com.net128.application.vaadin.petstore.model.Identifiable;
import com.net128.application.vaadin.petstore.repo.EntityChangeBroadcaster;
import com.net128.application.vaadin.petstore.ui.WorkingArea;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

@SpringComponent
@UIScope
@Slf4j
public abstract class EntityManager<T extends Identifiable> extends WorkingArea implements EntityChangeAware, EntityTyped<T> {
    @Getter
    final private EntityEditor<T> entityEditor;
    final private Grid<T> grid;
    final HorizontalLayout masterDetail = new HorizontalLayout();

    private Registration registration;

    public EntityManager(EntityEditor<T> entityEditor) {
        this.entityEditor = entityEditor;
        grid = new Grid<>(entityEditor.getTypeParameterClass());
    }

    public void layout() {
        UI.getCurrent().addShortcutListener(u -> entityEditor.editNew(), Key.KEY_N,
            KeyModifier.CONTROL, KeyModifier.ALT);

        grid.asSingleSelect().addValueChangeListener(
            e -> entityEditor.edit(e.getValue()));
        grid.setClassName("entity-grid");

        masterDetail.add(grid, entityEditor);
        entityEditor.setWidthFull();

        setupGrid(grid);
        HorizontalLayout actionBar = createActionBar(entityEditor);
        final Button newEntityButton = new Button("New "+getTypeName()+"...", VaadinIcon.PLUS.create());
        newEntityButton.addClickListener(e -> entityEditor.editNew());
        actionBar.add(newEntityButton);

        actionBar.setMargin(true);
        add(actionBar, masterDetail);
        updateGrid();
    }

    protected void updateGrid() {
        grid.setItems(filter());
        grid.setWidthFull();
        grid.setVerticalScrollingEnabled(true);
//        grid.addSelectionListener(event -> {
//            Set<T> selected = event.getAllSelectedItems();
//            System.out.println(selected.size() + " items selected");
//        });
//        grid.addFocusListener(event -> {
//            Grid grid = event.getSource();
//            System.out.println(grid.getSelectedItems() + " items selected");
//        });
        masterDetail.setWidthFull();
    }

    public void entityChanged(Identifiable entity) {
        updateGrid();
        entityEditor.entityChanged(entity);
    }

    protected void onAttach(AttachEvent attachEvent) {
        UI ui = attachEvent.getUI();
        registration = EntityChangeBroadcaster.register(
            entity -> ui.access(() -> entityChanged(entity)));
        layout();
    }

    protected void onDetach(DetachEvent detachEvent) {
        registration.remove();
        registration = null;
    }

    public abstract void setupGrid(Grid<T> grid);
    public abstract HorizontalLayout createActionBar(EntityEditor<T> entityEditor);
    public abstract List<T> filter();
}
