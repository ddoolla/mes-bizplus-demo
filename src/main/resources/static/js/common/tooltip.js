const tooltip =  {

    init() {
        document.querySelectorAll('[data-bs-toggle="tooltip"]')
            .forEach(element => {
                new bootstrap.Tooltip(element)
            });
    },
};

export default tooltip;