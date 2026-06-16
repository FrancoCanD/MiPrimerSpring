    const ValidacionesForm = {
        // Valida que se haya seleccionado una opción válida en el formulario de la tabla
        validarBotonPersona: function(boton,evento) {
            // Obtiene el formulario específico que activó el submit
            const formulario = boton.closest('form');
            // Busca el selector de personas dentro de este formulario
            const selector = formulario.querySelector('select[name="personaId"]');
            // Si no existe o se quedó con la opción por defecto ("")
            if (!selector || selector.value === "") {
                evento.preventDefault(); // Bloquea el envío a Spring Boot
                alert("Debes vincular un usuario");
                return false;
            }
            return true;
        }
    };