# DragDropLayouts



Currently when you want to drag and drop between layouts you will have to wrap the source component in a DragAndDropWrapper. 
Many times this is adequate but it tends to add some complexity to the code and yet another element in the DOM. It would 
be much nicer if the layouts would handle this for you.

To solve this issue I have extended the core layouts and implemented the necessary functionality so the layouts are valid 
drag sources and drop targets. This means that in your code you don't have to concern yourself with the drag and drop when 
adding components, only enable drag&drop in the layout and you are ready to go!

Currently supported layouts are:

* AbsoluteLayout (DDAbsoluteLayout)
* HorizontalLayout (DDHorizontalLayout)
* VerticalLayout (DDVerticalLayout)
* GridLayout (DDGridLayout)
* HorizontalSplitPanel (DDHorizontalSplitPanel)
* VerticalSplitPanel (DDVerticalSplitPanel)
* TabSheet (DDTabSheet)
* Accordion (DDAccordion)
* CssLayout (DDCssLayout)
* FormLayout (DDFormLayout)
* Panel (DDPanel)

Please see the version table below to decide which version to use in your project:

| Vaadin | 	DragDropLayouts |
|--------|------------------|
| 6.x 	 | 0.x              |
| 7.0.x+ | 1.0.x            |
| 7.2.x+ | 1.1.x            |
| 7.6.x+ | 1.2.x            |
