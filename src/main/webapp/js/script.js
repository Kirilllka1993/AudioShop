function showForm(formId, event) {
    var activeForm = document.querySelector(".active-section"),
        inactiveForm = document.getElementById(formId),
        activeTab = document.querySelector("main .nav-menu-item-active"),
        inactiveTab = event;
    if (!classie.has(inactiveTab, "nav-menu-item-active")) {
        classie.remove(inactiveForm, "inactive-section");
        classie.add(inactiveForm, "active-section");
        classie.remove(activeForm, "active-section");
        classie.add(activeForm, "inactive-section");

        classie.remove(inactiveTab, "nav-menu-item-inactive");
        classie.add(inactiveTab, "nav-menu-item-active");
        classie.remove(activeTab, "nav-menu-item-active");
        classie.add(activeTab, "nav-menu-item-inactive");
    }
}
