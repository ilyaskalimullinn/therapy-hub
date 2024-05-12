class RegistrationDto {
    constructor(email, fullName, password, passwordRepeat, role) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.passwordRepeat = passwordRepeat;
        this.role = role;
    }

    toRepresentation() {
        return {
            "fullName": this.fullName,
            "username": this.email,
            "password": this.password,
            "passwordRepeat": this.passwordRepeat,
            "role": this.role,
        }
    }
}

class LoginDto {
    constructor(email, password) {
        this.email = email;
        this.password = password;
    }

    toRepresentation() {
        return {
            "username": this.email,
            "password": this.password,
        }
    }
}


class SearchFilterDto {
    constructor() {
        this.searchString = "";
        this.filterParams = {
            price: {
                max: null,
                min: null
            },
            minRating: null,
            specialtyList: []
        };
        this.order = {
            by: "rating",
            type: "descending"
        };
    }
}

export { RegistrationDto, LoginDto, SearchFilterDto };
