export class BinderQueryModel {
    constructor(reference: string, sex: string) {
        this.reference = reference;
        this.sex = sex;
    }
    reference: string;
    sex: string;

    clear() {
        delete this.reference;
        delete this.sex;
    }
}
