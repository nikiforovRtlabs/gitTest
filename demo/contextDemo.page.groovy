import net.n2oapp.context.StaticSpringContext
import net.n2oapp.framework.api.metadata.control.plain.N2oInputText
import net.n2oapp.framework.api.metadata.global.view.N2oPage
import net.n2oapp.framework.api.metadata.global.view.tools.N2oEdit
import net.n2oapp.framework.api.metadata.global.view.widget.N2oFieldSet
import net.n2oapp.framework.api.metadata.global.view.widget.N2oForm
import net.n2oapp.framework.api.ui.FormModel
import net.n2oapp.framework.context.smart.impl.ContextEngine

contextDemo = new N2oPage()
contextDemo.id = "contextDemo"
contextDemo.objectId = "contextDemo"
contextDemo.layout = "n2o/layout/single"
contextDemo.name = "Контекст"

N2oPage.Container container = new N2oPage.Container()
container.id = "main"
container.page = contextDemo
container.place = "single"

N2oForm form = new N2oForm()
form.edit = new N2oEdit(model: FormModel.DEFAULT)
form.objectId = "contextDemo"
container.widget = form

def map = StaticSpringContext.getBean("smartContextEngine", ContextEngine.class).providerBeans
def fieldSets = []
for (String name : map?.keySet()) {
    N2oForm.FieldSetContainer fieldSetContainer = new N2oForm.FieldSetContainer()
    fieldSetContainer.header = N2oForm.FieldSetContainer.Header.line
    N2oFieldSet fieldSet = new N2oFieldSet()

    fieldSet.label = "Имя провайдера: ${name}, Класс провайдера: ${map.get(name).getClass().getSimpleName()}"
    def blocks = []

    N2oFieldSet.Block block = new N2oFieldSet.Block()
    block.isRow = false
    def fields = []

    for (String param : map.get(name)?.params) {
        N2oInputText field = new N2oInputText()
        field.id = param
        field.label = param
        field.defaultValue = '#{' + param + '?}'
        fields.add(field)
    }

    block.fields = fields
    blocks.add(block)
    fieldSet.blocks = blocks
    fieldSetContainer.fieldSet = fieldSet
    fieldSets.add(fieldSetContainer)
}

form.fieldSetContainers = fieldSets
containers = []
containers.add(container)
contextDemo.containers = containers