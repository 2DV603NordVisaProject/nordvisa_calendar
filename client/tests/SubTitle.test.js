- should be defined
- should render
- default children should work
- children sent should work

describe('SubTitle', () => {
  let wrapper;

  describe('Without child', () => {
    beforeEach(() => {
      wrapper = shallow(<SubTitle/>);
    });

    it("should be defined", () => {
      expect(wrapper).toBeDefined();
    });

    it("should render", () => {
      expect(wrapper.find("h3").length).toBe(1);
    });

    it("defaultProp for children should be set", () => {
      expect(wrapper.props().children).toBe("Sub Title");
    });
  });

  describe('with child', () => {
    beforeEach(() => {
      wrapper = shallow(<SubTitle>Hello World</SubTitle>);
    });

    it("should be defined", () => {
      expect(wrapper).toBeDefined();
    });

    it("should render", () => {
      expect(wrapper.find("h3").length).toBe(1);
    });

    it("defaultProp for children should be set", () => {
      expect(wrapper.props().children).toBe("Hello World");
    });
  });
});
