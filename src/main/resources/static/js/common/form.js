window.Mes = window.Mes || {};

// todo 삭제예정
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