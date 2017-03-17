/*
    Login dialog, properties:
    accountCreated, expandCreateAccountHandler, createAccountExpanded, createAccountHandler,
    newAccountErrorText, show, loginErrorText, loginHandler

*/
class Login extends React.Component{

    constructor(props) {
        super(props);
        this.state = {username: '', email: '', password: '', password2: ''};
        this.handleChange = this.handleChange.bind(this);
    }

    validateName(value) {
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

            let accountCreation = null;
            if (this.props.accountCreated) {
                accountCreation = "Account created, you can now login";
            }
            else {
                accountCreation = <div>Or <a name="newAccount" onClick={this.props.expandCreateAccountHandler}>Create New Account</a>

                <ReactBootstrap.Panel collapsible expanded={this.props.createAccountExpanded}>
                <form onSubmit={this.props.createAccountHandler}>
                    <ReactBootstrap.FormGroup validationState={this.validateName(this.state.username)}>
                          <ReactBootstrap.FormControl type="text" id="username" placeholder="Username" onChange={this.handleChange}/>
                          <ReactBootstrap.FormControl.Feedback />
                    </ReactBootstrap.FormGroup>
                    <ReactBootstrap.FormGroup validationState={this.validateEmail(this.state.email)}>
                          <ReactBootstrap.FormControl type="text" id="email" placeholder="Email" onChange={this.handleChange} />
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
                          <ReactBootstrap.Button
                                className="btn btn-primary btn-large centerButton"
                                disabled={
                                    this.validateName(this.state.username)=='error' ||
                                    this.validateEmail(this.state.email)=='error' ||
                                    this.validateNotEmpty(this.state.password)=='error' ||
                                    this.validateEqual(this.state.password, this.state.password2)=='error'
                                }
                                type="submit">Create Account</ReactBootstrap.Button>
                          <p>{this.props.newAccountErrorText}</p>

                 </form>
                 </ReactBootstrap.Panel>
                 </div>;
            }

        return (
         <ReactBootstrap.Modal show={this.props.show} bsSize="small">
          <ReactBootstrap.Modal.Header>
            <ReactBootstrap.Modal.Title>Welcome to Expenses</ReactBootstrap.Modal.Title>
          </ReactBootstrap.Modal.Header>
          <ReactBootstrap.Modal.Body>
            Login
            <form onSubmit={this.props.loginHandler}>
                <ReactBootstrap.FormGroup>
                      <ReactBootstrap.FormControl type="text" id="username" placeholder="Username"/>
                </ReactBootstrap.FormGroup>
                <ReactBootstrap.FormGroup>
                      <ReactBootstrap.FormControl type="password" id="password" placeholder="Password" />
                </ReactBootstrap.FormGroup>
                      <ReactBootstrap.Button className="btn btn-primary btn-large centerButton" type="submit">Login</ReactBootstrap.Button>
                      <p>{this.props.loginErrorText}</p>
             </form>
             <hr />
            {accountCreation}
          </ReactBootstrap.Modal.Body>
        </ReactBootstrap.Modal>
        )
    }
}