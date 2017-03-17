/*
    show, saveUserHandler, saveUserErrorText, editObject
*/
class EditUserDialog extends React.Component{

     constructor(props) {
        super(props);
        this.state = {username: '', email: '', password: '', password2: ''};
        this.handleChange = this.handleChange.bind(this);
        this.validateName = this.validateName.bind(this);
    }

    validateName(value) {
        if (this.props.editObject!=null) return 'success';
        var r = new RegExp("(^[a-zA-Z0-9]+$)");
        return r.test(value)? 'success': 'error';
    }

    validateEmail(value) {
        var r = new RegExp("(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$)");
        return r.test(value)? 'success': 'error';
    }

    validateNotEmpty(value) {
        const length = value.length;
        var r = 'error';
        if (length > 0) r = 'success';
        return r;
    }

    validateEqual(a, b) {
        var r = 'error';
        if (a != '' && a === b) return 'success';
        return r;
    }

    handleChange(event) {
        var returnObj = {};
        returnObj[event.target.id] = event.target.value;
        this.setState(returnObj);
    }

    render() {

        var editObject = this.props.editObject;
        var title = (editObject==null)? 'New User': 'Edit User';

        var user = (editObject==null)? '': editObject.name;
        var email = (editObject==null)? '': editObject.email;

        var existingUser = (editObject==null)? '': editObject._links.self.href;
        var editing = (editObject!=null);

        return (
         <ReactBootstrap.Modal show={this.props.show} onHide={this.props.onHide} bsSize="small">
          <ReactBootstrap.Modal.Header closeButton>
            <ReactBootstrap.Modal.Title>{title}</ReactBootstrap.Modal.Title>
          </ReactBootstrap.Modal.Header>
          <ReactBootstrap.Modal.Body>

            <form onSubmit={this.props.saveUserHandler}>

                <input type="hidden" id="existingUser" value={existingUser} />

                <ReactBootstrap.FormGroup validationState={this.validateName(this.state.username)}>
                      <ReactBootstrap.FormControl type="text" id="username" placeholder="Username"
                            defaultValue={user} onChange={this.handleChange} disabled={editing}/>
                      <ReactBootstrap.FormControl.Feedback />
                </ReactBootstrap.FormGroup>
                <ReactBootstrap.FormGroup validationState={this.validateEmail(this.state.email)}>
                      <ReactBootstrap.FormControl type="text" id="email" placeholder="Email" defaultValue={email} onChange={this.handleChange} />
                      <ReactBootstrap.FormControl.Feedback />
                </ReactBootstrap.FormGroup>
                <ReactBootstrap.FormGroup validationState={this.validateNotEmpty(this.state.password)}>
                      <ReactBootstrap.FormControl type="password" id="password" placeholder="Password" onChange={this.handleChange} />
                      <ReactBootstrap.FormControl.Feedback />
                </ReactBootstrap.FormGroup>
                <ReactBootstrap.FormGroup validationState={this.validateEqual(this.state.password, this.state.password2)}>
                      <ReactBootstrap.FormControl type="password" id="password2" placeholder="Password Again" onChange={this.handleChange}/>
                      <ReactBootstrap.FormControl.Feedback />
                </ReactBootstrap.FormGroup>

                Role: <ReactBootstrap.FormControl componentClass="select" id="role">
                            <option value="ROLE_USER" key="user">user</option>;
                            <option value="ROLE_USER_MANAGER" key="manager">manager</option>;
                            <option value="ROLE_ADMIN" key="admin">admin</option>;
                      </ReactBootstrap.FormControl>


                      <ReactBootstrap.Button
                            className="btn btn-primary btn-large centerButton"
                            disabled={
                                this.validateName(this.state.username)=='error' ||
                                this.validateEmail(this.state.email)=='error' ||
                                this.validateNotEmpty(this.state.password)=='error' ||
                                this.validateEqual(this.state.password, this.state.password2)=='error'
                            }
                            type="submit">Save</ReactBootstrap.Button>
                      <p>{this.props.saveUserErrorText}</p>

             </form>


          </ReactBootstrap.Modal.Body>
        </ReactBootstrap.Modal>
        )
    }
}