window.Mes = window.Mes || {};

 Mes.Form = {

     set(form, data) {
         Object.entries(data).forEach(([key, value]) => {

             const element = form.elements[key];

             if (!element) {
                return;
             }

             element.value = value ?? '';
         });
     },

     clear(form) {
         form.reset();
     },
 }